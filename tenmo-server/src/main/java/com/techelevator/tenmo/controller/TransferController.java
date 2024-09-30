package com.techelevator.tenmo.controller;

import com.sun.xml.bind.v2.TODO;
import com.techelevator.tenmo.dao.AccountRepository;
import com.techelevator.tenmo.dao.TransferRepository;
import com.techelevator.tenmo.dao.UserRepository;
import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/transfers/history")
    public List<Transfer> findHistory(Principal principal) {
        List<Transfer> transfers = new ArrayList<>();
        String username = principal.getName();
        int accountId = accountRepository.findAccountByUsername(username).getId();
        for (Transfer transfer : transferRepository.findAll()){
            if (transfer.getAccountFrom() == accountId || transfer.getAccountTo() == accountId){
                transfers.add(transfer);
            }
        }
        return transfers;
    }

    @GetMapping("/transfers/pending")
    public List<Transfer> findAllPending(Principal principal) {
        List<Transfer> transfers = new ArrayList<>();
        String username = principal.getName();
        int accountId = accountRepository.findAccountByUsername(username).getId();
        for (Transfer transfer : transferRepository.findAll()){
            if (transfer.getAccountFrom() == accountId && transfer.getTransferStatusId() == 1){
                transfers.add(transfer);
            }
        }
        return transfers;
    }

    @PostMapping("transfers/request")
    @ResponseStatus(HttpStatus.CREATED)
    public Transfer requestTransfer(@Valid @RequestBody Transfer transfer, Principal principal) {
        String username = principal.getName();
        Account requestingAccount = accountRepository.findAccountByUsername(username);
        transfer.setAccountTo(requestingAccount.getId());
        transfer.setTransferTypeId(1);
        transfer.setTransferStatusId(1);
        if(transfer.getAccountTo() == transfer.getAccountFrom()){
            throw new DaoException("You can not request money from yourself.");
        }
        return transferRepository.save(transfer);
    }

    @PostMapping("transfers/send")
    @ResponseStatus(HttpStatus.CREATED)
    public Transfer sendTransfer(@Valid @RequestBody Transfer transfer, Principal principal) {
        String username = principal.getName();
        Account userAccount = accountRepository.findAccountByUsername(username);
        Account accountTo = accountRepository.getOne(transfer.getAccountTo());
        transfer.setAccountFrom(userAccount.getId());
        transfer.setTransferTypeId(2);
        transfer.setTransferStatusId(1);
        if(transfer.getAccountTo() == transfer.getAccountFrom()){
            throw new DaoException("You can not transfer money to yourself.");
        }
        return transferRepository.approveTransfer(transfer, userAccount, accountTo, accountRepository);
    }

    @PatchMapping("transfers/{id}/approve")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Transfer decideTransfer(@PathVariable int id, Principal principal){
        Transfer transfer = transferRepository.getOne(id);
        String currentUsername = principal.getName();
        Account currentUserAccount = accountRepository.findAccountByUsername(currentUsername);
        Account accountTo = accountRepository.getOne(transfer.getAccountTo());
        if (currentUserAccount.getId() != transfer.getAccountFrom()){
            throw new DaoException("Not authorized to approve this transfer request.");
        }
        return transferRepository.approveTransfer(transfer, currentUserAccount, accountTo, accountRepository);
    }

}

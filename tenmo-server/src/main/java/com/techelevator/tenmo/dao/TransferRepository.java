package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

public interface TransferRepository extends JpaRepository <Transfer, Integer> {

    @Transactional
    default public Transfer approveTransfer(@Valid Transfer transfer, @Valid Account accountFrom, @Valid Account accountTo, AccountRepository ar){
        if (accountFrom.getId() == accountTo.getId()){
            throw new DaoException("The sending account must be different than the receiving account.");
        }
        if(transfer.getTransferStatusId() != 1) {
            throw new DaoException("Only pending transfers can be approved.");
        }

        transfer.setTransferStatusId(2);
        double exchangeAmount = transfer.getAmount();
        double fromStartingAmount = accountFrom.getBalance();
        double toStartingAmount = accountTo.getBalance();
        accountFrom.setBalance(fromStartingAmount - exchangeAmount);
        accountTo.setBalance(toStartingAmount + exchangeAmount);
        ar.save(accountFrom);
        ar.save(accountTo);
        return this.save(transfer);
    }

}

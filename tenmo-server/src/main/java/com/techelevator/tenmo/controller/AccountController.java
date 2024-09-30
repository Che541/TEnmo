package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountRepository;
import com.techelevator.tenmo.dao.UserRepository;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/accounts")
    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    @GetMapping("/accounts/{id}")
    public Account findById(@PathVariable int id) {
        return accountRepository.findById(id).get();
    }

    @GetMapping("/accounts/users/{userId}")
    public Account findByUserId(@PathVariable int userId) {
        return accountRepository.findAccountByUserId(userId);
    }

    @GetMapping("accounts/balance")
    public double findBalance(Principal principal) {
        String currentUsername = principal.getName();
        Account currentUserAccount = accountRepository.findAccountByUsername(currentUsername);
        return currentUserAccount.getBalance();
    }

}

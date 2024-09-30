package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.validation.Valid;


public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("SELECT a FROM Account a WHERE a.userId = (SELECT u FROM User u WHERE u.username = :username)")
    Account findAccountByUsername(String username);

    default Account updateAccount(@Valid Account account){
        return this.save(account);
    }

}

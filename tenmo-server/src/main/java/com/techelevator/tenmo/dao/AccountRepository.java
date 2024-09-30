package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("SELECT a FROM Account a WHERE a.userId = (SELECT u FROM User u WHERE u.username = :username)")
    Account findAccountByUsername(String username);


    @Query("SELECT a FROM Account a WHERE a.userId = :userId")
    Account findAccountByUserId(int userId);


    default Account updateAccount(@Valid Account account){
        return this.save(account);
    }

}

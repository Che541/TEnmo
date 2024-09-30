package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findUserByUsername(String username);


    @Query("SELECT u.username FROM User u WHERE u.id = (SELECT a.userId FROM Account a WHERE a.id = :id)")
    String findUsernameByAccountId(int id);


}

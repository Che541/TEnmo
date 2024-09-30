package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.UserRepository;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/username/{username}")
    public User findUserByUsername(@PathVariable String username) {
        return userRepository.findUserByUsername(username);
    }

    @GetMapping("/users")
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User findUserById(@PathVariable int id) {
        return userRepository.findById(id).get();
    }


    @GetMapping("/users/username/account/{id}")
    public String findUsernameByAccountId(@PathVariable int id, Principal principal) {
        String username = userRepository.findUsernameByAccountId(id);
        if (principal.getName().equals(username)){
            return "Me Myselfandi";
        }
        return username;
    }

}

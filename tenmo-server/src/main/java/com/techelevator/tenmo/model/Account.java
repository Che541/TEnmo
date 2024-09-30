package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
public class Account {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "account_id")
    private int id;
    @Column (name = "user_id")
    @NotNull
    private int userId;
    @JsonIgnore
    @Column (name = "balance")
    @DecimalMin("0")
    @NotNull
    private double balance;

    public Account() {
    }

    public Account(int userId, double balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

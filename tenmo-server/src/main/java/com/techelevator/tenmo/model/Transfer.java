package com.techelevator.tenmo.model;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
public class Transfer {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "transfer_id")
    private int id;
    @Column(name = "transfer_type_id")
    @NotNull
    private int transferTypeId;
    @Column(name = "transfer_status_id")
    @NotNull
    private int transferStatusId;
    @Column(name = "account_from")
    @NotNull
    private int accountFrom;
    @Column(name = "account_to")
    @NotNull
    private int accountTo;
    @Column(name = "amount")
    @DecimalMin("0")
    @NotNull
    private double amount;

    public Transfer() {
    }

    public Transfer(int transferTypeId, int transferStatusId, int accountFrom, double amount, int accountTo) {
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountFrom = accountFrom;
        this.amount = amount;
        this.accountTo = accountTo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

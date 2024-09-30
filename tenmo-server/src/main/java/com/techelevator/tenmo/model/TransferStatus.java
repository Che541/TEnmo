package com.techelevator.tenmo.model;

import javax.persistence.*;

@Entity
public class TransferStatus {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "transfer_status_id")
    private int id;
    @Column (name = "transfer_status_desc")
    private String transferStatusDesc;

    public TransferStatus() {
    }

    public TransferStatus(String transferStatusDesc) {
        this.transferStatusDesc = transferStatusDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransferStatusDesc() {
        return transferStatusDesc;
    }

    public void setTransferStatusDesc(String transferStatusDesc) {
        this.transferStatusDesc = transferStatusDesc;
    }
}

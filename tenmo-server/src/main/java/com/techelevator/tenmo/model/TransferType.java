package com.techelevator.tenmo.model;

import javax.persistence.*;

@Entity
public class TransferType {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "transfer_type_id")
    private int id;
    @Column(name = "transfer_type_desc")
    private String transferTypeDesc;

    public TransferType() {
    }

    public TransferType(String transferTypeDesc) {
        this.transferTypeDesc = transferTypeDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransferTypeDesc() {
        return transferTypeDesc;
    }

    public void setTransferTypeDesc(String transferTypeDesc) {
        this.transferTypeDesc = transferTypeDesc;
    }
}

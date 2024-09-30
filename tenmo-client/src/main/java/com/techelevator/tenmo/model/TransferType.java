package com.techelevator.tenmo.model;

public class TransferType {
    private int id;
    private String transferTypeDesc;
    
    public TransferType() {
    }

    public TransferType(int id, String transferTypeDesc) {
        this.id = id;
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

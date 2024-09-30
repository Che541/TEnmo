package com.techelevator.tenmo.model;

public class TransferStatus {
    private int id;
    private String transferStatusDesc;
    
    public TransferStatus(int id, String transferStatusDesc) {
        this.id = id;
        this.transferStatusDesc = transferStatusDesc;
    }

    public TransferStatus() {
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

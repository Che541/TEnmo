package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferStatusRepository;

import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransferStatusController {

    @Autowired
    private TransferStatusRepository transferStatusRepository;

    @GetMapping("/transferstatuses")
    public List<TransferStatus> findAllTransferStatuses() {
        return transferStatusRepository.findAll();
    }

    @GetMapping("/transferstatuses/{id}")
    public TransferStatus findById(@PathVariable int id) {
        return transferStatusRepository.findById(id);
    }


}

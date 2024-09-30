package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferTypeRepository;
import com.techelevator.tenmo.model.TransferType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferTypeController {

    @Autowired
    private TransferTypeRepository transferTypeRepository;

    @GetMapping("/transfertypes")
    public List<TransferType> findAllTransferTypes() {
        return transferTypeRepository.findAll();
    }

    @GetMapping("/transfertypes/{id}")
    public TransferType findById(@PathVariable int id, Principal principal) {
        System.out.println(principal.getName());
        return transferTypeRepository.findById(id);
    }

    @RequestMapping(path = "/whoami")
    public String whoAmI(Principal principal) {
        return principal.getName();
    }


}

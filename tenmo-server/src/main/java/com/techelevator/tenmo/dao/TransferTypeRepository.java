package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferTypeRepository extends JpaRepository<TransferType, Integer> {
    TransferType findById(int id);
}

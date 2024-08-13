package com.example.bankcustomerapi.controller;

import com.example.bankcustomerapi.model.BankCustomer;
import com.example.bankcustomerapi.repository.BankCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class BankCustomerController {

    @Autowired
    private BankCustomerRepository bankCustomerRepository;

    @GetMapping
    public List<BankCustomer> getAllCustomers() {
        return bankCustomerRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<BankCustomer> createCustomer(@RequestBody BankCustomer customer) {
        BankCustomer savedCustomer = bankCustomerRepository.save(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    // Additional endpoints can be added here
}

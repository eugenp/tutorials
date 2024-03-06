package com.baeldung.spring.servicevalidation.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.spring.servicevalidation.domain.UserAccount;
import com.baeldung.spring.servicevalidation.service.UserAccountService;

@RestController
public class UserAccountController {

    @Autowired
    private UserAccountService service;

    @PostMapping("/addUserAccount")
    public ResponseEntity<?> addUserAccount(@RequestBody UserAccount userAccount) {
        try {
            return ResponseEntity.ok(service.addUserAccount(userAccount));
        } catch(ConstraintViolationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

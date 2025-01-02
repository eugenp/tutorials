package com.baeldung.beanvalidation.application.controllers;

import com.baeldung.beanvalidation.application.entities.AppUser;
import com.baeldung.beanvalidation.application.repositories.AppUserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/app-users")
public class AppUserController {

    @Autowired
    private AppUserRepository appUserRepository;

    @PostMapping
    public ResponseEntity<String> addAppUser(@Valid @RequestBody AppUser appUser, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        appUserRepository.save(appUser);
        return new ResponseEntity<>("AppUser created successfully", HttpStatus.CREATED);
    }
}
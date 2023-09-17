package com.baeldung.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.dto.BooleanObject;

@RestController
public class ValidationController {

    @PostMapping("/validateBoolean")
    public ResponseEntity<String> addUser(@RequestBody @Valid BooleanObject booleanObj) {
        return ResponseEntity.ok("BooleanObject is valid");
    }
}

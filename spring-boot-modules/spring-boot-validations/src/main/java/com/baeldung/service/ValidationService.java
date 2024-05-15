package com.baeldung.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.baeldung.dto.BooleanObject;

import jakarta.validation.Valid;

@Service
@Validated
public class ValidationService {

    public void processBoolean(@Valid BooleanObject booleanObj) {
        // further processing
    }
}

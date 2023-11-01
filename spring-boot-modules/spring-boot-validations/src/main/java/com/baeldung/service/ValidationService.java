package com.baeldung.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.baeldung.dto.BooleanObject;

@Service
@Validated
public class ValidationService {

    public void processBoolean(@Valid BooleanObject booleanObj) {
        // further processing
    }
}

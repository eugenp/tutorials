package com.baeldung.jcommander.usagebilling.cli.validator;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.util.UUID;

public class UUIDValidator implements IParameterValidator {

    @Override
    public void validate(String name, String value) throws ParameterException {
        try {
            UUID.fromString(value);

        } catch (IllegalArgumentException e) {
            throw new ParameterException("String parameter " + value + " is not a valid UUID.");
        }
    }
}

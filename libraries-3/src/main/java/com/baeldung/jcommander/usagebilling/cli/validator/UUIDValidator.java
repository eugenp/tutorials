package com.baeldung.jcommander.usagebilling.cli.validator;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.util.regex.Pattern;

public class UUIDValidator implements IParameterValidator {

    private static final String UUID_REGEX = 
      "[0-9a-fA-F]{8}(-[0-9a-fA-F]{4}){3}-[0-9a-fA-F]{12}";

    @Override
    public void validate(String name, String value) throws ParameterException {
        if (!isValidUUID(value)) {
            throw new ParameterException(
              "String parameter " + value + " is not a valid UUID.");
        }
    }

    private boolean isValidUUID(String value) {
        return Pattern
          .compile(UUID_REGEX)
          .matcher(value).matches();
    }
}

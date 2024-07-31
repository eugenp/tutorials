package com.baeldung.javaxval.notnull;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;

public class NotNullMethodParameter {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    public int doesNotValidateNotNull(@NotNull String myString) {
        return myString.length();
    }

    public int validateNotNull(@NotNull String myString) {
        validator.validate(myString);
        return myString.length();
    }

}
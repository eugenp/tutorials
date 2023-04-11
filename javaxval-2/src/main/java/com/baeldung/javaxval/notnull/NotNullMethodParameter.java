package com.baeldung.javaxval.notnull;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;

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
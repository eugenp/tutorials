package com.baeldung.validation.listvalidation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CustomConstraintValidator implements ConstraintValidator<CustomConstraint, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

}

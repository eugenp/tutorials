package com.baeldung.restvalidation.service3;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldNotEmptyValidator implements ConstraintValidator<FieldNotEmpty, Object> {

    private String message;
    private String field;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return (value != null && !value.toString().trim().isEmpty());
    }

}
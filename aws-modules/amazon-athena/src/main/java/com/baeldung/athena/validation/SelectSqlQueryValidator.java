package com.baeldung.athena.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SelectSqlQueryValidator implements ConstraintValidator<SelectSqlQuery, String> {

    @Override
    public boolean isValid(String query, ConstraintValidatorContext context) {
        return query.toUpperCase().startsWith("SELECT");
    }

}
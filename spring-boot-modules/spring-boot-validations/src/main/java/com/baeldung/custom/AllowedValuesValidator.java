package com.baeldung.custom;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class AllowedValuesValidator implements ConstraintValidator<AllowedValues, List<String>> {

    private List<String> allowedValues;

    @Override
    public void initialize(AllowedValues constraintAnnotation) {
        allowedValues = Arrays.asList(constraintAnnotation.values());
    }

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        return values != null && values.stream()
            .allMatch(allowedValues::contains);
    }
}

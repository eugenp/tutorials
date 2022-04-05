package com.baeldung.javaxval.methodvalidation.constraints;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class ConsistentDateParameterValidator implements ConstraintValidator<ConsistentDateParameters, Object[]> {

    @Override
    public boolean isValid(Object[] value, ConstraintValidatorContext context) {

        if (value[0] == null || value[1] == null) {
            return true;
        }

        if (!(value[0] instanceof LocalDate) || !(value[1] instanceof LocalDate)) {
            throw new IllegalArgumentException("Illegal method signature, expected two parameters of type LocalDate.");
        }

        return ((LocalDate) value[0]).isAfter(LocalDate.now()) && ((LocalDate) value[0]).isBefore((LocalDate) value[1]);
    }
}

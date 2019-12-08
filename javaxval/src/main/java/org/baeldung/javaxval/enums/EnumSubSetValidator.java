package org.baeldung.javaxval.enums;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public abstract class EnumSubSetValidator<T extends Annotation, U> implements ConstraintValidator<T, U> {
    private U[] subset;

    protected void initialize(U[] subset) {
        this.subset = subset;
    }

    @Override
    public boolean isValid(U value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return Arrays.asList(subset)
            .contains(value);
    }
}

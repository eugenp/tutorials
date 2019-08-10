package com.baeldung.validation.listvalidation.constraint;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.baeldung.validation.listvalidation.model.Movie;

public class MaxSizeConstraintValidator implements ConstraintValidator<MaxSizeConstraint, List<Movie>> {

    @Override
    public boolean isValid(List<Movie> values, ConstraintValidatorContext context) {
        boolean isValid = true;
        if (values == null || values.isEmpty()) {
            isValid = false;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Movie list cannot be empty.")
                .addConstraintViolation();
        } else if (values.size() > 4) {
            isValid = false;
        }
        return isValid;
    }
    
}

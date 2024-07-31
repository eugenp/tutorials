package com.baeldung.validation.listvalidation.constraint;

import java.util.List;

import com.baeldung.validation.listvalidation.model.Movie;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MaxSizeConstraintValidator implements ConstraintValidator<MaxSizeConstraint, List<Movie>> {

    @Override
    public boolean isValid(List<Movie> values, ConstraintValidatorContext context) {
       return values.size() <= 4;
    }

}

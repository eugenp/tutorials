package com.baeldung.validation.listvalidation.constraint;

import java.util.List;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.baeldung.validation.listvalidation.model.Movie;

public class CustomConstraintValidator implements ConstraintValidator<CustomConstraint, List<Movie>> {

    @Override
    public boolean isValid(List<Movie> values, ConstraintValidatorContext context) {
        boolean isValid = true;
        if (values == null || values.isEmpty()) {
            isValid = false;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Movie list cannot be empty.")
                .addConstraintViolation();
        }
        String regex = "[^A-Za-z0-9].*";
        for (Movie movie : values) {
            if (Pattern.matches(regex, movie.getName())) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

}

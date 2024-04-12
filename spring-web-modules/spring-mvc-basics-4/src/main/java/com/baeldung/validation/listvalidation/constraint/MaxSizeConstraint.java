package com.baeldung.validation.listvalidation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = MaxSizeConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxSizeConstraint {

    String message() default "The input list cannot contain more than 4 movies.";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}

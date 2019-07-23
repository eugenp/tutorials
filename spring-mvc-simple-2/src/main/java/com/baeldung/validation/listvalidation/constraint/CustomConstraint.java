package com.baeldung.validation.listvalidation.constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = CustomConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomConstraint {

    String message() default "Invalid movie name.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

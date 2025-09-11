package com.baeldung.customstatefulvalidation.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AvailableChannelValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AvailableChannel {
    String message() default "must be available tenant channel";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
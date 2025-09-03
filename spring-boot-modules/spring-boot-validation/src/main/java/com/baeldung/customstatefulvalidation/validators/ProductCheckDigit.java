package com.baeldung.customstatefulvalidation.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ProductCheckDigitValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductCheckDigit {
    String message() default "must have valid check digit";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
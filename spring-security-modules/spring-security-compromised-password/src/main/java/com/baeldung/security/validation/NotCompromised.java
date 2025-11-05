package com.baeldung.security.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CompromisedPasswordValidator.class)
public @interface NotCompromised {

    String message() default "The provided password is compromised and cannot be used.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
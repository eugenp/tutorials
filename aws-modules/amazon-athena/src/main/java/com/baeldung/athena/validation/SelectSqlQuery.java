package com.baeldung.athena.validation;

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
@Constraint(validatedBy = SelectSqlQueryValidator.class)
public @interface SelectSqlQuery {

    String message() default "Only SELECT SQL queries allowed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
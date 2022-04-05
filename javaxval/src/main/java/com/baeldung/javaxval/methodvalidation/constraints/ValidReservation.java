package com.baeldung.javaxval.methodvalidation.constraints;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ValidReservationValidator.class)
@Target({ METHOD, CONSTRUCTOR })
@Retention(RUNTIME)
@Documented
public @interface ValidReservation {

    String message() default "End date must be after begin date and both must be in the future, room number must be bigger than 0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

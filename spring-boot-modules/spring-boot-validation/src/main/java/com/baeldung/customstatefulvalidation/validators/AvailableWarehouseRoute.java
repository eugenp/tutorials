package com.baeldung.customstatefulvalidation.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AvailableWarehouseRouteValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AvailableWarehouseRoute {
    String message() default "chosen warehouse route must be active";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
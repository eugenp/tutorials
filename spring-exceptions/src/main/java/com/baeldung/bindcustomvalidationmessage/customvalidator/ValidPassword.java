package com.baeldung.bindcustomvalidationmessage.customvalidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "{user.password.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

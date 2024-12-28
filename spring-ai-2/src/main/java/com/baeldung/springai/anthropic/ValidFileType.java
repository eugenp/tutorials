package com.baeldung.springai.anthropic;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileTypeValidator.class)
public @interface ValidFileType {

    String message() default "Invalid file type. Allowed types are: JPG, PNG, GIF, WEBP, and PDF";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
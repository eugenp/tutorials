package com.baeldung.textract.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TextractFileValidator.class)
public @interface ValidFileType {

    String message() default "Invalid file type. Allowed types are PNG, JPEG, TIFF, and PDF.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
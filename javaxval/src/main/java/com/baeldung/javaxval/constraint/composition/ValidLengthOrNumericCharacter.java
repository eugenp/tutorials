package com.baeldung.javaxval.constraint.composition;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Pattern(regexp = ".*\\d.*", message = "must contain at least one numeric character")
@Length(min = 6, max = 32, message = "must have between 6 and 32 characters")
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})
@ConstraintComposition(CompositionType.OR)
public @interface ValidLengthOrNumericCharacter {

    String message() default "field should have a valid length or contain numeric character(s).";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}


package com.baeldung.yavi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import am.ik.yavi.arguments.IntegerValidator;
import am.ik.yavi.arguments.StringValidator;
import am.ik.yavi.builder.IntegerValidatorBuilder;
import am.ik.yavi.builder.StringValidatorBuilder;
import am.ik.yavi.core.Validated;

public class PrimitiveUnitTest {
    @Test
    void givenValidatorForNonBlank_whenValidatingAValidString_thenValidationSucceeds() {
        StringValidator<String> validator = StringValidatorBuilder.of("name", c -> c.notBlank())
            .build();

        Validated<String> validate = validator.validate("Hello");
        assertTrue(validate.isValid());
    }

    @Test
    void givenValidatorForNonBlank_whenValidatingABlankString_thenValidationFails() {
        StringValidator<String> validator = StringValidatorBuilder.of("name", c -> c.notBlank())
            .build();

        Validated<String> validate = validator.validate("");
        assertFalse(validate.isValid());
        assertEquals(1, validate.errors().size());
    }

    @Test
    void givenValidatorForNonNegative_whenValidatingAPositiveNumber_thenValidationSucceeds() {
        IntegerValidator<Integer> validator = IntegerValidatorBuilder.of("age", c -> c.positiveOrZero())
            .build();

        Validated<Integer> validate = validator.validate(42);
        assertTrue(validate.isValid());
    }

    @Test
    void givenValidatorForNonNegative_whenValidatingANegativeNumber_thenValidationFails() {
        IntegerValidator<Integer> validator = IntegerValidatorBuilder.of("age", c -> c.positiveOrZero())
            .build();

        Validated<Integer> validate = validator.validate(-1);
        assertFalse(validate.isValid());
        assertEquals(1, validate.errors().size());
    }
}

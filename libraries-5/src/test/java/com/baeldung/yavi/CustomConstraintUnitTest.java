package com.baeldung.yavi;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.CustomConstraint;
import am.ik.yavi.core.Validator;
import org.junit.jupiter.api.Test;

import java.net.Inet4Address;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomConstraintUnitTest {
    record Data(String palindrome) {}

    @Test
    void givenLambdaValidatorForPalindrome_whenPalindromeIsValid_thenValidationSucceeds() {
        Validator<Data> validator = ValidatorBuilder.of(Data.class)
            .constraint(Data::palindrome, "palindrome",
                c -> c.predicate(s -> validatePalindrome(s), "palindrome.valid", "\"{0}\" must be a palindrome"))
            .build();

        ConstraintViolations result = validator.validate(new Data("racecar"));
        assertTrue(result.isValid());
    }

    @Test
    void givenLambdaValidatorForPalindrome_whenPalindromeIsInvalid_thenValidationFails() {
        Validator<Data> validator = ValidatorBuilder.of(Data.class)
            .constraint(Data::palindrome, "palindrome",
                c -> c.predicate(s -> validatePalindrome(s), "palindrome.valid", "\"{0}\" must be a palindrome"))
            .build();

        ConstraintViolations result = validator.validate(new Data("other"));
        assertFalse(result.isValid());
        assertEquals(1, result.size());
        assertEquals("palindrome", result.get(0).name());
        assertEquals("palindrome.valid", result.get(0).messageKey());
    }

    @Test
    void givenClassValidatorForPalindrome_whenPalindromeIsValid_thenValidationSucceeds() {
        Validator<Data> validator = ValidatorBuilder.of(Data.class)
            .constraint(Data::palindrome, "palindrome", c -> c.predicate(new PalindromeConstraint()))
            .build();

        ConstraintViolations result = validator.validate(new Data("racecar"));
        assertTrue(result.isValid());
    }

    @Test
    void givenClassValidatorForPalindrome_whenPalindromeIsInvalid_thenValidationFails() {
        Validator<Data> validator = ValidatorBuilder.of(Data.class)
            .constraint(Data::palindrome, "palindrome", c -> c.predicate(new PalindromeConstraint()))
            .build();

        ConstraintViolations result = validator.validate(new Data("other"));
        assertFalse(result.isValid());
        assertEquals(1, result.size());
        assertEquals("palindrome", result.get(0).name());
        assertEquals("palindrome.valid", result.get(0).messageKey());
    }

    boolean validatePalindrome(String input) {
        String reversed = new StringBuilder()
            .append(input)
            .reverse()
            .toString();

        return input.equals(reversed);
    }

    class PalindromeConstraint implements CustomConstraint<String> {
        @Override
        public boolean test(String input) {
            String reversed = new StringBuilder()
                .append(input)
                .reverse()
                .toString();

            return input.equals(reversed);
        }

        @Override
        public String messageKey() {
            return "palindrome.valid";
        }

        @Override
        public String defaultMessageFormat() {
            return "\"{0}\" must be a palindrome";
        }
    }
}

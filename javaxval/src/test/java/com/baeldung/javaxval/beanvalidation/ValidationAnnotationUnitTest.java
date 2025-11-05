package com.baeldung.javaxval.beanvalidation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class ValidationAnnotationUnitTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void whenUserValuesAreValid_thenValidationSucceeds() {
        User user = new User();
        user.setName("test-name");
        user.setWorking(true);
        user.setAboutMe("test-about-me");
        user.setAge(24);
        user.setEmail("test@baeldung.ut");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }

    @Test
    void whenUserValuesAreInvalid_thenValidationFails() {
        User user = new User();
        user.setName(null);
        user.setWorking(false);
        user.setAboutMe("test");
        user.setAge(10);
        user.setEmail("test-invalid-email");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
        assertThat(violations).hasSize(5);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
          .containsExactlyInAnyOrder(
            "Name cannot be null",
            "Working must be true", 
            "About Me must be between 10 and 200 characters",
            "Age should not be less than 18", 
            "Email should be valid");
    }

}
package com.baeldung.bindcustomvalidationmessage.externalfilevalidation;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDTOExtValidationTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsValid_thenNoValidationErrors() {
        UserDTO user = new UserDTO();
        user.setName("John");
        user.setEmail("john@example.com");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenNameIsNull_thenValidationFails() {
        UserDTO user = new UserDTO();
        user.setName(null);
        user.setEmail("john@example.com");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);

        ConstraintViolation<UserDTO> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
        assertThat(violation.getMessage()).isEqualTo("User name must not be null");
    }

    @Test
    void whenNameTooShort_thenValidationFails() {
        UserDTO user = new UserDTO();
        user.setName("A");
        user.setEmail("john@example.com");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);

        ConstraintViolation<UserDTO> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
        assertThat(violation.getMessage()).isEqualTo("User name must be at least 2 characters long");
    }

    @Test
    void whenEmailIsInvalid_thenValidationFails() {
        UserDTO user = new UserDTO();
        user.setName("John");
        user.setEmail("invalid-email");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);

        ConstraintViolation<UserDTO> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("email");
        assertThat(violation.getMessage()).isEqualTo("Please provide a valid email");
    }

    @Test
    void whenEmailIsNull_thenValidationFails() {
        UserDTO user = new UserDTO();
        user.setName("John");
        user.setEmail(null);

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);

        ConstraintViolation<UserDTO> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("email");
        assertThat(violation.getMessage()).isEqualTo("Email is required");
    }
}

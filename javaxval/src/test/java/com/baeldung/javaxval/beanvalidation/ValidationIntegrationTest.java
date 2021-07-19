package com.baeldung.javaxval.beanvalidation;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

public class ValidationIntegrationTest {

    private Validator validator;

    @Before
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private User createUser() {
        User user = new User();
        user.setName("John");
        user.setWorking(true);
        user.setAge(18);
        return user;
    }

    @Test
    public void ifNameIsNull_nameValidationFails() {
        User user = new User();
        user.setWorking(true);
        user.setAboutMe("Its all about me!!");
        user.setAge(50);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(violations.isEmpty(), false);
    }

    @Test
    public void ifSizeNotInRange_aboutMeValidationFails() {
        User user = new User();
        user.setName("MyName");
        user.setAboutMe("Its all about me!!");
        user.setAge(50);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(violations.isEmpty(), false);
    }

    @Test
    public void ifWorkingIsFalse_workingValidationFails() {
        User user = new User();
        user.setName("MyName");
        user.setAboutMe("Its all about me!!");
        user.setAge(50);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(violations.isEmpty(), false);
    }

    @Test
    public void ifAgeNotRange_ageValidationFails() {
        User user = new User();
        user.setName("MyName");
        user.setAboutMe("Its all about me!!");
        user.setAge(8);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(violations.isEmpty(), false);
    }

    @Test
    public void ifFnameNullAgeNotRangeAndWorkingIsFalse_validationFailsWithThreeErrors() {
        User user = new User();
        user.setAboutMe("Its all about me!!");
        user.setAge(300);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(violations.isEmpty(), false);
        assertEquals(violations.size(), 3);
    }

    @Test
    public void givenInvalidEmail_thenValidationFails() {
        User user = createUser();
        user.setEmail("john");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void givenBlankPreference_thenValidationFails() {
        User user = createUser();
        user.setPreferences(Collections.singletonList(" "));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void givenEmptyOptional_thenValidationSucceeds() {
        User user = createUser();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size());
    }

    @Test
    public void givenPastDateOfBirth_thenValidationSuccess() {
        User user = createUser();
        user.setDateOfBirth(LocalDate.of(1980, 5, 20));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size());

    }
}

package com.baeldung.validation;

import java.io.File;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.bval.jsr.ApacheValidationProvider;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.baeldung.model.User;

public class ValidationIntegrationTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeClass
    public static void setup() {
        validatorFactory = Validation.byProvider(ApacheValidationProvider.class)
            .configure()
            .buildValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void givenUser_whenValidate_thenValidationViolations() {
        User user = new User("ana@yahoo.com", "pass", "nameTooLong_______________", 15);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue("no violations", violations.size() > 0);
    }
	
    @Test
    public void givenInvalidAge_whenValidateProperty_thenConstraintViolation() {
        User user = new User("ana@yahoo.com", "pass", "Ana", 12);

        Set<ConstraintViolation<User>> propertyViolations = validator.validateProperty(user, "age");
        assertEquals("size is not 1", 1, propertyViolations.size());
    }
	
    @Test
    public void givenValidAge_whenValidateValue_thenNoConstraintViolation() {
        User user = new User("ana@yahoo.com", "pass", "Ana", 18);
    
        Set<ConstraintViolation<User>> valueViolations = validator.validateValue(User.class, "age", 20);
        assertEquals("size is not 0", 0, valueViolations.size());
    }

    @Test
    public void whenValidateNonJSR_thenCorrect() {
        User user = new User("ana@yahoo.com", "pass", "Ana", 20);
        user.setCardNumber("1234");
        user.setIban("1234");
        user.setWebsite("10.0.2.50");
        user.setMainDirectory(new File("."));
		
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "iban");
        assertEquals("size is not 1", 1, violations.size());

        violations = validator.validateProperty(user, "website");
        assertEquals("size is not 0", 0, violations.size());

        violations = validator.validateProperty(user, "mainDirectory");
        assertEquals("size is not 0", 0, violations.size());
    }

    @Test
    public void givenInvalidPassword_whenValidatePassword_thenConstraintViolation() {
        User user = new User("ana@yahoo.com", "password", "Ana", 20);
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "password");
        assertEquals("message incorrect", "Invalid password", violations.iterator()
            .next()
            .getMessage());
    }
	
    @Test
    public void givenValidPassword_whenValidatePassword_thenNoConstraintViolation() {
        User user = new User("ana@yahoo.com", "password#", "Ana", 20);
		
        Set<ConstraintViolation<User>>  violations = validator.validateProperty(user, "password");
        assertEquals("size is not 0", 0, violations.size());
    }

    @AfterClass
    public static void close() {
        if (validatorFactory != null) {
            validatorFactory.close();
        }
    }
}

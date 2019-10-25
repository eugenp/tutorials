package org.baeldung.javaxval.messageinterpolator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParameterMessageInterpolaterIntegrationTest {

    private static Validator validator;

    @BeforeClass
    public static void beforeClass() {
        ValidatorFactory validatorFactory = Validation.byDefaultProvider()
          .configure()
          .messageInterpolator(new ParameterMessageInterpolator())
          .buildValidatorFactory();

        validator = validatorFactory.getValidator();
    }

    @Test
    public void ifNameLengthIsLess_nameValidationFails() {
        Person person = new Person();
        person.setName("John Doe");
        person.setAge(18);

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertEquals(1, violations.size());

        ConstraintViolation<Person> violation = violations.iterator().next();
        assertEquals("Name should be between 10 and 100 characters", violation.getMessage());
    }

    @Test
    public void ifAgeIsLess_ageMinValidationFails() {
        Person person = new Person();
        person.setName("John Stephaner Doe");
        person.setAge(16);

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertEquals(1, violations.size());

        ConstraintViolation<Person> violation = violations.iterator().next();
        assertEquals("Age should not be less than 18", violation.getMessage());
    }
    
    @Test
    public void ifEmailIsMalformed_emailFormatValidationFails() {
        Person person = new Person();
        person.setName("John Stephaner Doe");
        person.setAge(18);
        person.setEmail("johndoe.dev");
        
        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertEquals(1, violations.size());
        
        ConstraintViolation<Person> violation = violations.iterator().next();
        assertEquals("Email address should be in a correct format: ${validatedValue}", violation.getMessage());
    }

}
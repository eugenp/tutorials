package com.baeldung.beanvalidation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.baeldung.beanvalidation.container.IntegerContainer;
import com.baeldung.beanvalidation.container.StringContainer;
import com.baeldung.beanvalidation.model.User;
import com.baeldung.beanvalidation.service.UserService;
import java.util.Set;
import java.util.stream.Collectors;
import static org.junit.Assert.*;

public class ValidationTest {

    private static Validator validator;
    private static User user;
    
    @BeforeClass
    public static void setUpHibernateValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    
    @BeforeClass
    public static void setUpUserInstance() {
        user = new User();
    }
    
    @AfterClass
    public static void tearDownHibernateValidatorInstance() {
        validator = null;
    }
    
    @AfterClass
    public static void tearDownUserInstance() {
        user = null;
    }
    
    @Test
    public void givenNullName_whenValidated_thenMessageDescriptorForName() {
        user.setName(null);
        user.setEmail("mary@domain.com");
        user.setAge(36);
        assertEquals("Name cannot be null", validator.validate(user).stream().map(violation -> violation.getMessage()).collect(Collectors.joining()));
    }
    
    @Test
    public void givenInvalidEmail_whenValidated_thenMessageDescriptorforEmail() {
        user.setName("Mary");
        user.setEmail("no-email");
        user.setAge(36);
        assertEquals("Email must be a well-formed email address", validator.validate(user).stream().map(violation -> violation.getMessage()).collect(Collectors.joining()));
    }
    
    @Test
    public void givenAgeLesserThanLowerBound_whenValidated_thenMessageDescriptorforAge() {
        user.setName("Mary");
        user.setEmail("mary@domain.com");
        user.setAge(0);
        assertEquals("Age must not be lesser than 1", validator.validate(user).stream().map(violation -> violation.getMessage()).collect(Collectors.joining()));
    }
    
    @Test
    public void givenAgeGreaterThanUpperBound_whenValidated_thenMessageDescriptorforAge() {
        user.setName("Mary");
        user.setEmail("mary@domain.com");
        user.setAge(100);
        assertEquals("Age must not be greater than 99", validator.validate(user).stream().map(violation -> violation.getMessage()).collect(Collectors.joining()));
    }
    
    @Test
    public void givenNullFileName_whenValidated_thenMessageDescriptorforFileName() {
        user.setName("Mary");
        user.setEmail("mary@domain.com");
        user.setAge(36);
        UserService userService = new UserService();
        userService.setFileName(null);
        userService.setUser(user);
        assertEquals("FileName cannot be null", validator.validate(userService).stream().map(violation -> violation.getMessage()).collect(Collectors.joining()));
    }
    
    @Test
    public void givenFileNameShortherThanLowerBound_whenValidated_thenMessageDescriptorforFileName() {
        user.setName("Mary");
        user.setEmail("mary@domain.com");
        user.setAge(36);
        UserService userService = new UserService();
        userService.setFileName("");
        userService.setUser(user);
        assertEquals("FileName must be between 5 and 10 characters", validator.validate(userService).stream().map(violation -> violation.getMessage()).collect(Collectors.joining()));
    }
    
    @Test
    public void givenFileNameLongerThanUpperBound_whenValidated_thenMessageDescriptorforFileName() {
        user.setName("Mary");
        user.setEmail("mary@domain.com");
        user.setAge(36);
        UserService userService = new UserService();
        userService.setFileName("waytoolongfilename");
        userService.setUser(user);
        assertEquals("FileName must be between 5 and 10 characters", validator.validate(userService).stream().map(violation -> violation.getMessage()).collect(Collectors.joining()));
    }
    
    @Test
    public void givenNullUserAndNullFileName_whenValidated_thenTwoConstraintViolations() {
        user.setName(null);
        user.setEmail("mary@domain.com");
        user.setAge(36);
        UserService userService = new UserService();
        userService.setFileName(null);
        userService.setUser(user);
        Set<ConstraintViolation<UserService>> constraintViolations = validator.validate(userService);
        assertEquals(2, constraintViolations.size());
    }
    
    @Test
    public void givenNullElement_whenValidated_thenOneConstraintViolation() {
        StringContainer container = new StringContainer();
        container.addElement(null);
        Set<ConstraintViolation<StringContainer>> constraintViolations = validator.validate(container);
        assertEquals(1, constraintViolations.size());
    }
    
    @Test
    public void givenNegativeInteger_whenValidated_thenOneConstraintViolation() {
        IntegerContainer container = new IntegerContainer();
        container.addElement(-1);
        Set<ConstraintViolation<IntegerContainer>> constraintViolations = validator.validate(container);
        assertEquals(1, constraintViolations.size());
    }
}

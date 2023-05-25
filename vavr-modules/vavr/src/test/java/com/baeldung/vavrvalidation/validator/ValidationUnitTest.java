package com.baeldung.vavrvalidation.validator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.baeldung.vavrvalidation.model.User;
import io.vavr.collection.Seq;
import io.vavr.control.Either.Right;
import io.vavr.control.Validation.Invalid;
import io.vavr.control.Validation.Valid;

public class ValidationUnitTest {
	
    private static UserValidator userValidator;
    
    @BeforeClass
    public static void setUpUserValidatorInstance() {
        userValidator = new UserValidator();
    }
    
    @AfterClass
    public static void teardownUserValidatorInstance() {
        userValidator = null;
    }
    
    @Test
    public void givenInvalidUserParams_whenValidated_thenInvalidInstance() {
        assertThat(userValidator.validateUser("John", "no-email"), instanceOf(Invalid.class));
    }
    
    @Test
    public void givenValidUserParams_whenValidated_thenValidInstance() {
        assertThat(userValidator.validateUser("John", "john@domain.com"), instanceOf(Valid.class));
    }
    
    @Test
    public void givenInvalidUserParams_whenValidated_thenTrueWithIsInvalidMethod() {
        assertTrue(userValidator.validateUser("John", "no-email").isInvalid());
    }

    @Test
    public void givenValidUserParams_whenValidated_thenTrueWithIsValidMethod() {
        assertTrue(userValidator.validateUser("John", "john@domain.com").isValid());
    }
    
    @Test
	public void givenInValidUserParams_withGetErrorMethod_thenGetErrorMessages() {
        assertEquals("Name contains invalid characters, Email must be a well-formed email address", userValidator.validateUser(" ", "no-email")
          .getError().intersperse(", ").fold("", String::concat));
    }
    
    @Test
    public void givenValidUserParams_withGetMethod_thenGetUserInstance() {
        assertThat(userValidator.validateUser("John", "john@domain.com").get(), instanceOf(User.class));
    }
    
    @Test
    public void givenValidUserParams_withtoEitherMethod_thenRightInstance() {
        assertThat(userValidator.validateUser("John", "john@domain.com").toEither(), instanceOf(Right.class));
    }
    
    @Test
    public void givenValidUserParams_withFoldMethodWithListlengthUserHashCode_thenEqualstoParamsLength() {
        assertEquals(2, (int) userValidator.validateUser(" ", " ").fold(Seq::length, User::hashCode));
    }
}

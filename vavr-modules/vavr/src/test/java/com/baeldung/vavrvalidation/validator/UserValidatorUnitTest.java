package com.baeldung.vavrvalidation.validator;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.Test;
import com.baeldung.vavrvalidation.validator.UserValidator;
import io.vavr.control.Validation.Invalid;
import io.vavr.control.Validation.Valid;

public class UserValidatorUnitTest {
    
    @Test
    public void givenValidUserParams_whenValidated_thenInstanceOfValid() {
        UserValidator userValidator = new UserValidator();
        assertThat(userValidator.validateUser("John", "john@domain.com"), instanceOf(Valid.class));
    }
    
    @Test
    public void givenInvalidUserParams_whenValidated_thenInstanceOfInvalid() {
        UserValidator userValidator = new UserValidator();
        assertThat(userValidator.validateUser("John", "no-email"), instanceOf(Invalid.class));
    }
}

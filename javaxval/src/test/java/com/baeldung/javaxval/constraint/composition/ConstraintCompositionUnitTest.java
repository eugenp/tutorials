package com.baeldung.javaxval.constraint.composition;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ConstraintCompositionConfig.class }, loader = AnnotationConfigContextLoader.class)
public class ConstraintCompositionUnitTest {

    @Autowired
    private AccountService accountService;

    private Validator validator;

    @Before
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenUsernameIsInvalid_validationShouldReturnTwoViolations() {
        Account account = new Account();
        account.setNickname("valid_nickname123");
        account.setPassword("valid_password123");
        account.setUsername("john");

        Set<ConstraintViolation<Account>> violations = validator.validate(account);

        assertThat(violations).hasSize(2);
    }

    @Test
    public void whenPasswordIsInvalid_validationShouldReturnSingleViolation() {
        Account account = new Account();
        account.setUsername("valid_username123");
        account.setNickname("valid_nickname123");
        account.setPassword("john");

        Set<ConstraintViolation<Account>> violations = validator.validate(account);

        assertThat(violations).hasSize(1);
    }

    @Test
    public void whenNicknameIsTooShortButContainsNumericCharacter_validationShouldPass() {
        Account account = new Account();
        account.setUsername("valid_username123");
        account.setPassword("valid_password123");
        account.setNickname("doe1");

        Set<ConstraintViolation<Account>> violations = validator.validate(account);

        assertThat(violations).isEmpty();
    }

    @Test
    public void whenMethodReturnValuesIsInvalid_validationShouldFail() {
        assertThatThrownBy(() -> accountService.getAnInvalidAlphanumericValue()).isInstanceOf(ConstraintViolationException.class)
            .hasMessageContaining("must contain at least one numeric character")
            .hasMessageContaining("must have between 6 and 32 characters");
    }

}
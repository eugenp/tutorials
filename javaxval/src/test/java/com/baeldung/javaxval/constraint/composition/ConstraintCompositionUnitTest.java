package com.baeldung.javaxval.constraint.composition;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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

    public static final String VALID_ALPHANUMERIC_VALUE = "john_doe1234";

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
        Account account = aValidAccount();
        account.setUsername("john");

        Set<ConstraintViolation<Account>> violations = validator.validate(account);

        assertThat(violations).hasSize(2);
    }

    @Test
    public void whenPasswordIsInvalid_validationShouldReturnSingleViolation() {
        Account account = aValidAccount();
        account.setPassword("john");

        Set<ConstraintViolation<Account>> violations = validator.validate(account);

        assertThat(violations).hasSize(1);
    }

    @Test
    public void whenNicknameIsTooShortButContainsNumericCharacter_validationShouldPass() {
        Account account = aValidAccount();
        account.setNickname("jd1");

        Set<ConstraintViolation<Account>> violations = validator.validate(account);

        assertThat(violations).isEmpty();
    }

    @Test
    public void whenMethodReturnValuesIsInvalid_validationShouldFail() {
        assertThatThrownBy(() -> accountService.getAnInvalidAlphanumericValue()).isInstanceOf(ConstraintViolationException.class)
            .hasMessageContaining("must contain at least one numeric character")
            .hasMessageContaining("must have between 6 and 32 characters");
    }

    private Account aValidAccount() {
        Account account = new Account();
        account.setUsername(VALID_ALPHANUMERIC_VALUE);
        account.setNickname(VALID_ALPHANUMERIC_VALUE);
        account.setPassword(VALID_ALPHANUMERIC_VALUE);
        return account;
    }

}
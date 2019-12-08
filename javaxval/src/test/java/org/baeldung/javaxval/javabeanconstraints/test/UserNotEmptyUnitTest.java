package org.baeldung.javaxval.javabeanconstraints.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.baeldung.javaxval.javabeanconstraints.entities.UserNotEmpty;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserNotEmptyUnitTest {

    private static Validator validator;

    @BeforeClass
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory()
            .getValidator();
    }

    @Test
    public void whenNotEmptyName_thenNoConstraintViolations() {
        UserNotEmpty user = new UserNotEmpty("John");

        Set<ConstraintViolation<UserNotEmpty>> violations = validator.validate(user);

        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    public void whenEmptyName_thenOneConstraintViolation() {
        UserNotEmpty user = new UserNotEmpty("");

        Set<ConstraintViolation<UserNotEmpty>> violations = validator.validate(user);

        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void whenNullName_thenOneConstraintViolation() {
        UserNotEmpty user = new UserNotEmpty(null);

        Set<ConstraintViolation<UserNotEmpty>> violations = validator.validate(user);

        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void whenToString_thenCorrect() {
        UserNotEmpty user = new UserNotEmpty("John");

        assertThat(user.toString()).isEqualTo("User{name=John}");
    }
}

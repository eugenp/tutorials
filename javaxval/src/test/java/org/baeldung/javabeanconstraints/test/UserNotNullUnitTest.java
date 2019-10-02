package org.baeldung.javabeanconstraints.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.baeldung.javabeanconstraints.entities.UserNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserNotNullUnitTest {

    private static Validator validator;

    @BeforeClass
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory()
            .getValidator();
    }

    @Test
    public void whenNotNullName_thenNoConstraintViolations() {
        UserNotNull user = new UserNotNull("John");

        Set<ConstraintViolation<UserNotNull>> violations = validator.validate(user);

        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    public void whenNullName_thenOneConstraintViolation() {
        UserNotNull user = new UserNotNull(null);

        Set<ConstraintViolation<UserNotNull>> violations = validator.validate(user);

        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void whenEmptyName_thenNoConstraintViolations() {
        UserNotNull user = new UserNotNull("");

        Set<ConstraintViolation<UserNotNull>> violations = validator.validate(user);

        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    public void whenToString_thenCorrect() {
        UserNotNull user = new UserNotNull("John");

        assertThat(user.toString()).isEqualTo("User{name=John}");
    }
}

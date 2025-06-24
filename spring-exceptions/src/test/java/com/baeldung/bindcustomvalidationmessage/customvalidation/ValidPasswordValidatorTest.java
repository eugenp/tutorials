package com.baeldung.bindcustomvalidationmessage.customvalidation;

import static org.assertj.core.api.Assertions.assertThat;

import com.baeldung.bindcustomvalidationmessage.customvalidator.PasswordDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidPasswordValidatorTest {
  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void whenPasswordIsValid_thenNoViolations() {
    PasswordDTO dto = new PasswordDTO("Str0ngP@ssword123");

    Set<ConstraintViolation<PasswordDTO>> violations = validator.validate(dto);

    assertThat(violations).isEmpty();
  }

  @Test
  void whenPasswordLacksDigitsOrSpecials_thenValidationFails() {
    PasswordDTO dto = new PasswordDTO("password");

    Set<ConstraintViolation<PasswordDTO>> violations = validator.validate(dto);

    assertThat(violations).hasSize(1);
  }

  @Test
  void whenPasswordIsNull_thenValidationFails() {
    PasswordDTO dto = new PasswordDTO(null);

    Set<ConstraintViolation<PasswordDTO>> violations = validator.validate(dto);

    assertThat(violations).hasSize(1);
  }
}

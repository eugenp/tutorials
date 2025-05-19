package com.baeldung.stringmap;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import org.assertj.core.api.Assertions;
import org.hibernate.validator.constraints.Length;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanValidationStyleTest {

    @Test
    void givenInnerVariableMap_whenValidateIsCalled_thenValidationNotWorking() {
        Map<@Length(min = 10) String, @NotBlank String> givenMap = new HashMap<>();
        givenMap.put("tooShort", "");

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Map<String, String>>> violations = validator.validate(givenMap);

        Assertions.assertThat(violations).isEmpty(); // this shouldn't be empty
    }

    @Test
    void givenWrappedMap_whenValidateIsCalled_thenValidationWorking() {
        WrappedMap wrappedMap = new WrappedMap();
        Map<String, String> givenMap = new HashMap<>();
        givenMap.put("tooShort", "");
        wrappedMap.setMap(givenMap);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<WrappedMap>> violations = validator.validate(wrappedMap);

        Assertions.assertThat(violations).isNotEmpty();
    }


}

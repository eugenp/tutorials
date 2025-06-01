package com.baeldung.stringmap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MapValidatorTest {
    private MapValidator mapValidator;
    private static final String OBJECT_NAME = "targetMap"; // Consistent name for the binding result

    @BeforeEach
    void setUp() {
        mapValidator = new MapValidator();
    }

    @Test
    void givenMapValidator_whenSupportsCalledWithMapClass_thenReturnsTrue() {
        assertThat(mapValidator.supports(Map.class)).isTrue();
        assertThat(mapValidator.supports(HashMap.class)).isTrue(); // Also true for subclasses
        assertThat(mapValidator.supports(LinkedHashMap.class)).isTrue();
    }

    @Test
    void givenMapValidator_whenSupportsCalledWithNonMapClass_thenReturnsFalse() {
        assertThat(mapValidator.supports(String.class)).isFalse();
        assertThat(mapValidator.supports(Object.class)).isFalse();
        assertThat(mapValidator.supports(Integer.class)).isFalse();
    }

    @Test
    void givenValidMap_whenValidateIsCalled_thenValidationSucceeds() {
        Map<String, String> validMap = new LinkedHashMap<>();
        validMap.put("longEnoughKey1", "Valid Value 1");
        validMap.put("anotherValidKeyHere", "Valid Value 2");
        Errors errors = new MapBindingResult(new HashMap<>(), OBJECT_NAME);

        mapValidator.validate(validMap, errors);

        assertThat(errors.hasErrors()).isFalse();
    }

    @Test
    void givenMapWithNonStringKey_whenValidateIsCalled_thenRejectsWithInvalidTypeError() {
        Map<Object, Object> mapWithInvalidKey = new LinkedHashMap<>();
        mapWithInvalidKey.put(123, "Value for integer key"); // Invalid key type
        mapWithInvalidKey.put("validKeyString", "Valid Value");
        Errors errors = new MapBindingResult(new HashMap<>(), OBJECT_NAME);

        mapValidator.validate(mapWithInvalidKey, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getErrorCount()).isEqualTo(1);
        assertThat(errors.getFieldError("map[123]")).isNotNull();
        assertThat(errors.getFieldError("map[123]").getCode()).isEqualTo("map.entry.invalidType");
    }

    @Test
    void givenMapWithNonStringValue_whenValidateIsCalled_thenRejectsWithInvalidTypeError() {
        Map<Object, Object> mapWithInvalidValue = new LinkedHashMap<>();
        mapWithInvalidValue.put("validKeyString1", 12345); // Invalid value type
        mapWithInvalidValue.put("validKeyString2", "Valid Value");
        Errors errors = new MapBindingResult(new HashMap<>(), OBJECT_NAME);

        mapValidator.validate(mapWithInvalidValue, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getErrorCount()).isEqualTo(1);
        assertThat(errors.getFieldError("map[validKeyString1]")).isNotNull();
        assertThat(errors.getFieldError("map[validKeyString1]").getCode()).isEqualTo("map.entry.invalidType");
    }

    @Test
    void givenMapWithShortKey_whenValidateIsCalled_thenRejectsWithKeyTooShortError() {
        Map<String, String> mapWithShortKey = new LinkedHashMap<>();
        mapWithShortKey.put("shortKey", "Valid Value"); // Key too short
        mapWithShortKey.put("longEnoughKey1", "Another Valid Value");
        Errors errors = new MapBindingResult(new HashMap<>(), OBJECT_NAME);

        mapValidator.validate(mapWithShortKey, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getErrorCount()).isEqualTo(1);
        assertThat(errors.getFieldError("map[shortKey]")).isNotNull();
        assertThat(errors.getFieldError("map[shortKey]").getCode()).isEqualTo("key.tooShort");
    }

    @Test
    void givenMapWithBlankValue_whenValidateIsCalled_thenRejectsWithValueBlankError() {
        Map<String, String> mapWithBlankValue = new LinkedHashMap<>();
        mapWithBlankValue.put("longEnoughKey1", ""); // Blank value
        mapWithBlankValue.put("longEnoughKey2", "   "); // Blank value (whitespace)
        mapWithBlankValue.put("longEnoughKey3", "Valid Value");
        Errors errors = new MapBindingResult(new HashMap<>(), OBJECT_NAME);

        mapValidator.validate(mapWithBlankValue, errors);

        // Then: Errors should be reported for both blank values
        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getErrorCount()).isEqualTo(2);
        assertThat(errors.getFieldError("map[longEnoughKey1]")).isNotNull();
        assertThat(errors.getFieldError("map[longEnoughKey1]").getCode()).isEqualTo("value.blank");
        assertThat(errors.getFieldError("map[longEnoughKey2]")).isNotNull();
        assertThat(errors.getFieldError("map[longEnoughKey2]").getCode()).isEqualTo("value.blank");
    }

    @Test
    void givenMapWithNullValue_whenValidateIsCalled_thenRejectsWithInvalidTypeError() {
        Map<String, Object> mapWithNullValue = new LinkedHashMap<>();
        mapWithNullValue.put("longEnoughKey1", null); // Null value (will fail type check first)
        mapWithNullValue.put("longEnoughKey2", "Valid Value");
        Errors errors = new MapBindingResult(new HashMap<>(), OBJECT_NAME);

        mapValidator.validate(mapWithNullValue, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getErrorCount()).isEqualTo(1);
        assertThat(errors.getFieldError("map[longEnoughKey1]")).isNotNull();
        assertThat(errors.getFieldError("map[longEnoughKey1]").getCode()).isEqualTo("map.entry.invalidType");
    }

    @Test
    void givenMapWithMultipleValidationIssues_whenValidateIsCalled_thenReportsAllErrors() {
        Map<Object, Object> mapWithMultipleErrors = new LinkedHashMap<>();
        mapWithMultipleErrors.put("short", "Valid Value"); // Short key
        mapWithMultipleErrors.put("longEnoughKey1", ""); // Blank value
        mapWithMultipleErrors.put(123, "Value"); // Invalid key type
        mapWithMultipleErrors.put("longEnoughKey2", 456); // Invalid value type
        mapWithMultipleErrors.put("anotherValidKeyHere", "Good Value"); // Valid entry
        Errors errors = new MapBindingResult(new HashMap<>(), OBJECT_NAME);

        mapValidator.validate(mapWithMultipleErrors, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getErrorCount()).isEqualTo(4);

        // Check specific errors
        assertThat(errors.getFieldError("map[short]")).isNotNull();
        assertThat(errors.getFieldError("map[short]").getCode()).isEqualTo("key.tooShort");

        assertThat(errors.getFieldError("map[longEnoughKey1]")).isNotNull();
        assertThat(errors.getFieldError("map[longEnoughKey1]").getCode()).isEqualTo("value.blank");

        assertThat(errors.getFieldError("map[123]")).isNotNull();
        assertThat(errors.getFieldError("map[123]").getCode()).isEqualTo("map.entry.invalidType");

        assertThat(errors.getFieldError("map[longEnoughKey2]")).isNotNull();
        assertThat(errors.getFieldError("map[longEnoughKey2]").getCode()).isEqualTo("map.entry.invalidType");
    }

    @Test
    void givenEmptyMap_whenValidateIsCalled_thenValidationSucceeds() {
        Map<String, String> emptyMap = new HashMap<>();
        Errors errors = new MapBindingResult(new HashMap<>(), OBJECT_NAME);

        mapValidator.validate(emptyMap, errors);

        assertThat(errors.hasErrors()).isFalse();
    }
}
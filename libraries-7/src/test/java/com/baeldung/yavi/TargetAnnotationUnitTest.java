package com.baeldung.yavi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validator;
import am.ik.yavi.meta.ConstraintTarget;

public class TargetAnnotationUnitTest {
    record Person(@ConstraintTarget String name, @ConstraintTarget int age) {}

    private Validator<Person> validator = ValidatorBuilder.of(Person.class)
        .constraint(_TargetAnnotationUnitTest_PersonMeta.NAME, c -> c.notBlank())
        .constraint(_TargetAnnotationUnitTest_PersonMeta.AGE, c -> c.positiveOrZero().lessThan(150))
        .build();

    @Test
    void givenValidatorForPerson_whenValidatingAValidPerson_thenValidationSucceeds() {
        ConstraintViolations result = validator.validate(new Person("Baeldung", 42));
        assertTrue(result.isValid());
    }

    @Test
    void givenValidatorForPerson_whenValidatingABlankName_thenValidationFails() {
        ConstraintViolations result = validator.validate(new Person("", 42));
        assertFalse(result.isValid());
        assertEquals(1, result.size());
        assertEquals("name", result.get(0).name());
        assertEquals("charSequence.notBlank", result.get(0).messageKey());
    }

    @Test
    void givenValidatorForPerson_whenValidatingANegativeAge_thenValidationFails() {
        ConstraintViolations result = validator.validate(new Person("Baeldung", -1));
        assertFalse(result.isValid());
        assertEquals(1, result.size());
    }

    @Test
    void givenValidatorForPerson_whenValidatingABlankNameAndNegativeAge_thenValidationFails() {
        ConstraintViolations result = validator.validate(new Person("", -1));
        assertFalse(result.isValid());
        assertEquals(2, result.size());
    }

    @Test
    void givenValidatorForPerson_whenFailingFast_thenValidationFails() {
        ConstraintViolations result = validator.failFast(true).validate(new Person("", -1));
        assertFalse(result.isValid());
        assertEquals(1, result.size());
        assertEquals("name", result.get(0).name());
        assertEquals("charSequence.notBlank", result.get(0).messageKey());
    }
}

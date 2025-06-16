package com.baeldung.yavi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validator;

public class NestedRecordUnitTest {
    private Validator<Name> nameValidator = ValidatorBuilder.of(Name.class)
        .constraint(Name::firstName, "firstName", c -> c.notBlank())
        .constraint(Name::surname, "surname", c -> c.notBlank())
        .build();

    private Validator<Person> personValidator = ValidatorBuilder.of(Person.class)
        .nest(Person::name, "name", nameValidator)
        .constraint(Person::age, "age", c -> c.positiveOrZero().lessThan(150))
        .build();

    record Name(String firstName, String surname) {}
    record Person(Name name, int age) {}

    @Test
    void givenValidatorForPerson_whenValidatingAnInvalidName_thenValidationFails() {
        ConstraintViolations result = personValidator.validate(new Person(new Name("", ""), 42));
        assertFalse(result.isValid());

        assertEquals(2, result.size());
        assertEquals("name.firstName", result.get(0).name());
        assertEquals("charSequence.notBlank", result.get(0).messageKey());
        assertEquals("name.surname", result.get(1).name());
        assertEquals("charSequence.notBlank", result.get(1).messageKey());
    }
}

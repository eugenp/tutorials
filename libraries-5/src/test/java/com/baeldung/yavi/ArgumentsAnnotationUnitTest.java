package com.baeldung.yavi;

import am.ik.yavi.arguments.Arguments2Validator;
import am.ik.yavi.builder.ArgumentsValidatorBuilder;
import am.ik.yavi.core.Validated;
import am.ik.yavi.meta.ConstraintArguments;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ArgumentsAnnotationUnitTest {
    record Person(String name, int age) {
        @ConstraintArguments
        Person {
        }

        @ConstraintArguments
        boolean isOlderThan(int check) {
            return this.age() > check;
        }
    }

    @Test
    void givenAConstructorValidator_whenCalledWithInvalidArguments_thenNothingIsConstructed() {
        Arguments2Validator<String, Integer, Person> validator = ArgumentsValidatorBuilder.of(Person::new)
            .builder(b -> b
                .constraint(_ArgumentsAnnotationUnitTest_PersonArgumentsMeta.NAME, c -> c.notBlank())
                .constraint(_ArgumentsAnnotationUnitTest_PersonArgumentsMeta.AGE, c -> c.positiveOrZero())
            )
            .build();

        Validated<Person> result = validator.validate("", -1);
        assertFalse(result.isValid());
        assertEquals(2, result.errors().size());
        assertEquals("name", result.errors().get(0).name());
        assertEquals("charSequence.notBlank", result.errors().get(0).messageKey());
        assertEquals("age", result.errors().get(1).name());
        assertEquals("numeric.positiveOrZero", result.errors().get(1).messageKey());

        assertThrows(NoSuchElementException.class, () -> result.value());
    }

    @Test
    void givenAMethodValidator_whenCalledWithInvalidArguments_thenNothingIsDone() {
        Arguments2Validator<Person, Integer, Boolean> validator = ArgumentsValidatorBuilder.of(Person::isOlderThan)
            .builder(b -> b
                .constraint(_ArgumentsAnnotationUnitTest_PersonIsOlderThanArgumentsMeta.CHECK, c -> c.positiveOrZero())
            )
            .build();

        Person person = new Person("Baeldung", 42);
        Validated<Boolean> result = validator.validate(person, -1);
        assertFalse(result.isValid());
        assertEquals(1, result.errors().size());
        assertEquals("check", result.errors().get(0).name());
        assertEquals("numeric.positiveOrZero", result.errors().get(0).messageKey());

        assertThrows(NoSuchElementException.class, () -> result.value());
    }
}

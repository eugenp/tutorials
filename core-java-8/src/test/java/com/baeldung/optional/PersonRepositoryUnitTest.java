package com.baeldung.optional;

import org.junit.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PersonRepositoryUnitTest {

    PersonRepository personRepository = new PersonRepository();

    @Test
    public void whenIdIsNull_thenExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class,
                () ->
                        Optional
                                .ofNullable(personRepository.findNameById(null))
                                .orElseThrow(IllegalArgumentException::new));
    }

    @Test
    public void whenIdIsNonNull_thenNoExceptionIsThrown() {
        assertAll(
                () ->
                        Optional
                                .ofNullable(personRepository.findNameById("id"))
                                .orElseThrow(RuntimeException::new));
    }

    @Test
    public void whenIdNonNull_thenReturnsNameUpperCase() {
        String name = Optional
                .ofNullable(personRepository.findNameById("id"))
                .map(String::toUpperCase)
                .orElseThrow(RuntimeException::new);

        assertEquals("NAME", name);
    }

}
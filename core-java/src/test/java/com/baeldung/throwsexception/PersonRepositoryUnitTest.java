package com.baeldung.throwsexception;

import org.junit.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PersonRepositoryUnitTest {

    PersonRepository personRepository = new PersonRepository();

    @Test
    public void whenIdIsNull_thenExceptionIsThrown() throws Exception {
        assertThrows(Exception.class,
                () ->
                        Optional
                                .ofNullable(personRepository.findNameById(null))
                                .orElseThrow(Exception::new));
    }

    @Test
    public void whenIdIsNonNull_thenNoExceptionIsThrown() throws Exception {
        assertAll(
                () ->
                        Optional
                                .ofNullable(personRepository.findNameById("id"))
                                .orElseThrow(Exception::new));
    }

    @Test
    public void whenIdIsNonNull_thenShouldReturnNameUpperCase() throws Exception {
        String name = Optional
                .ofNullable(personRepository.findNameById("id"))
                .map(String::toUpperCase)
                .orElseThrow(Exception::new);

        assertEquals("NAME", name);
    }

}
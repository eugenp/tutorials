package com.baeldung.objectmutability;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImmutablePersonUnitTest {

    @Test
    public void givenImmutablePerson_whenAccessFields_thenCorrectValues() {
        ImmutablePerson person = new ImmutablePerson("John", 30);

        assertEquals("John", person.getName());
        assertEquals(30, person.getAge());
    }
}


package com.baeldung.lombok;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class DefaultConstructorPersonUnitTest {

    @Test
    public void givenPerson_whenUsingNoArgsConstructor_thenAssert() {
        DefaultConstructorPerson user = new DefaultConstructorPerson();

        assertNotNull(user);
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
    }

    @Test
    public void givenPerson_whenUsingAllArgsConstructor_thenAssert() {
        DefaultConstructorPerson user = new DefaultConstructorPerson(1L, "john_snow", "securePassword");

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("john_snow", user.getUsername());
        assertEquals("securePassword", user.getPassword());
    }
}
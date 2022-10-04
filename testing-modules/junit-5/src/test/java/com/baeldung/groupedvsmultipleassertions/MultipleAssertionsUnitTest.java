package com.baeldung.groupedvsmultipleassertions;

import groupedvsmultipleassertions.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MultipleAssertionsUnitTest {
    @Test
    void givenMultipleAssertions_whenSingleAssertStatementFails_thenRestWillNotBeExecuted() {
        User user = new User("baeldung", "support@baeldung.com", false);
        assertEquals("baeldung", user.getUsername(), "Username should be baeldung");
        assertEquals("admin@baeldung.com", user.getEmail(), "Email should be admin@baeldung.com");
        assertTrue(user.getActivated(), "User should be activated");
    }
}

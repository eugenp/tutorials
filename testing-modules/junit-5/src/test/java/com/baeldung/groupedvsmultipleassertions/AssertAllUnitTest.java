package com.baeldung.groupedvsmultipleassertions;

import groupedvsmultipleassertions.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AssertAllUnitTest {
    @Test
    void givenAssertAll_whenSingleAssertStatementFails_thenRestWillStillBeExecuted() {
        User user = new User("baeldung", "support@baeldung.com", false);
        assertAll(
                "Exploring assertAll behavior",
                () -> assertEquals("baeldung", user.getUsername(), "Username should be baeldung"),
                () -> assertEquals("admin@baeldung.com", user.getEmail(), "Email should be admin@baeldung.com"),
                () -> assertTrue(user.getActivated(), "User should be activated")
        );
    }
}

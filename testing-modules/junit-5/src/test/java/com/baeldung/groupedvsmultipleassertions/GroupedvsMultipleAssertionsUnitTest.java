package com.baeldung.groupedvsmultipleassertions;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupedvsMultipleAssertionsUnitTest {

    @Disabled
    @Test
    void givenAssertAll_whenSingleAssertStatementFails_thenRestWillStillBeExecuted() {
        User user = new User("baeldung", "support@baeldung.com", false);
        assertAll(
                "Grouped Assertions of User",
                () -> assertEquals("admin", user.getUsername(), "Username should be admin"),
                () -> assertEquals("admin@baeldung.com", user.getEmail(), "Email should be admin@baeldung.com"),
                () -> assertTrue(user.getActivated(), "User should be activated")
        );
    }

    @Disabled
    @Test
    void givenMultipleAssertions_whenSingleAssertStatementFails_thenRestWillNotBeExecuted() {
        User user = new User("baeldung", "support@baeldung.com", false);
        assertEquals("admin", user.getUsername(), "Username should be admin");
        assertEquals("admin@baeldung.com", user.getEmail(), "Email should be admin@baeldung.com");
        assertTrue(user.getActivated(), "User should be activated");
    }
}

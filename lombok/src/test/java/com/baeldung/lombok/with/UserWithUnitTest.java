package com.baeldung.lombok.with;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserWithUnitTest {

    @Test
    public void whenUserAuthenticated_thenClonedAndUpdated() {
        User immutableUser = new User("testuser", "test@mail.com", false);

        User authenticatedUser = immutableUser.withAuthenticated(true);

        assertNotSame(immutableUser, authenticatedUser);
        assertFalse(immutableUser.isAuthenticated());
        assertTrue(authenticatedUser.isAuthenticated());
    }
}
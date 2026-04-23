package com.baeldung.hashcode.application;

import com.baeldung.hashcode.standard.User;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ApplicationUnitTest {

    @Test
    public void main_NoInputState_TextPrintedToConsole() throws Exception {
        Map<User, User> users = new HashMap<>();
        User user1 = new User(1L, "John", "john@domain.com");
        User user2 = new User(2L, "Jennifer", "jennifer@domain.com");
        User user3 = new User(3L, "Mary", "mary@domain.com");

        users.put(user1, user1);
        users.put(user2, user2);
        users.put(user3, user3);

        assertTrue(users.containsKey(user1));
    }

    @Test
    public void givenOverriddenHashCode_whenUsingIdentityHashCode_thenJvmIdentityIsPreserved() {
        User user1 = new User(1L, "John", "john@domain.com");
        User user2 = new User(1L, "John", "john@domain.com");

        assertEquals(user1.hashCode(), user2.hashCode());

        assertNotEquals(System.identityHashCode(user1), System.identityHashCode(user2));
    }
}
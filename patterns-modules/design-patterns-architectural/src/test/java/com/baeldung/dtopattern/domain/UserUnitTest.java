package com.baeldung.dtopattern.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserUnitTest {


    @Test
    void whenUserIsCreated_shouldEncryptPassword() {
        User user = new User("Test", "test", new ArrayList<>());

        assertEquals(user.encrypt("test"), user.getPassword());
        assertNotEquals(user.encrypt("Test"), user.getPassword());
    }

    @Test
    void whenUserIsCreated_shouldFailIfNameIsNull() {
        assertThrows(NullPointerException.class, () ->
            new User(null, "test", new ArrayList<>()));
    }

    @Test
    void whenUserIsCreated_shouldFailIfPasswordIsNull() {
        assertThrows(NullPointerException.class, () ->
            new User("Test", null, new ArrayList<>()));
    }

    @Test
    void whenUserIsCreated_shouldFailIfRolesIsNull() {
        assertThrows(NullPointerException.class, () ->
            new User("Test", "Test", null));
    }

}

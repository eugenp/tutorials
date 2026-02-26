package com.baeldung;

import com.baeldung.mockingcollections.UserService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceUnitTest {

    @Test
    void givenList_whenRealCollectionIsUsed_thenShouldReturnFirstUser_() {

        List<String> users = new ArrayList<>();
        users.add("Joey");

        UserService userService = new UserService(users);

        assertTrue(userService.hasUsers());
        assertEquals("Joey", userService.getFirstUser());
    }

    @Test
    void givenEmptyList_whenUserListIsEmpty_thenShouldReturnNull() {

        List<String> users = new ArrayList<>();

        UserService userService = new UserService(users);

        assertNull(userService.getFirstUser());
    }
}

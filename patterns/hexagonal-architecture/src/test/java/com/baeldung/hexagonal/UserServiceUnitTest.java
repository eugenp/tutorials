package com.baeldung.hexagonal;

import com.baeldung.hexagonal.core.user.User;
import com.baeldung.hexagonal.core.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class UserServiceUnitTest {

    @Autowired
    private UserService userService;

    @Test
    public void whenCreatingUser_ThenSuccessful() {
        userService.createUser("test@baeldung.com", "Baeldung");
    }

    @Test
    public void whenCreatingUser_ThenSuccessfulRetrieval() {
        String email = "baledung@baledung.com";
        userService.createUser(email, "Baeldung");
        User user = userService.getUser(email);
        assertNotNull(user);
        assertEquals(email, user.getEmail());
    }

    @Test
    public void whenSearchingNonExistentUser_ThenReturnNull() {
        String randomEmail = UUID.randomUUID().toString();
        User nonExistentUser = userService.getUser(randomEmail);
        assertNull(nonExistentUser);
    }

}

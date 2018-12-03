package com.baeldung.hexagonal.primary.adapters;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.hexagonal.secondary.adapters.InMemoryUserRepository;
import com.baeldung.hexagonal.secondary.ports.UserRepository;
import com.baeldung.hexagonal.service.DefaultUserService;
import com.baeldung.hexagonal.service.UserService;

public class DefaultUserInterfaceUnitTest {

    private DefaultUserInterface defaultUserInterface;

    @BeforeEach
    public void setUp() {
        UserRepository userRepository = new InMemoryUserRepository();
        UserService userService = new DefaultUserService(userRepository);
        defaultUserInterface = new DefaultUserInterface(userService);
    }

    @Test
    public void givenUserAndPassword_whenUserDoesNotExist_thenLoginFails() {
        String actual = defaultUserInterface.loginUser("Tom", "123456");
        assertThat(actual, is("User not able to login due to incorrect username or password"));
    }

    @Test
    public void givenUserAndPassword_whenUserIsNew_thenRegistrationSuccess() {
        String actual = defaultUserInterface.registerUser("Tom", "123456");
        assertThat(actual, is("User registration success"));
    }

    @Test
    public void givenUserAndPassword_whenUserAlreadyExists_thenRegistrationFails() {
        // Registering a new user
        defaultUserInterface.registerUser("Tom", "123456");

        // Registering again with the same credential
        String actual = defaultUserInterface.registerUser("Tom", "123456");
        assertThat(actual, is("User registration failed"));
    }

    @Test
    public void givenUserAndPassword_whenUserExists_thenLoginSuccess() {
        // Registering a new user
        defaultUserInterface.registerUser("Tom", "123456");

        // Login with the same user
        String actual = defaultUserInterface.loginUser("Tom", "123456");
        assertThat(actual, is("User is logged in successfully"));
    }

}

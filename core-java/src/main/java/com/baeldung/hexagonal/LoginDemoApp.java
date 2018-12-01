package com.baeldung.hexagonal;

import com.baeldung.hexagonal.primary.adapters.DefaultUserInterface;
import com.baeldung.hexagonal.primary.ports.UserInterface;
import com.baeldung.hexagonal.secondary.adapters.InMemoryUserRepository;
import com.baeldung.hexagonal.secondary.ports.UserRepository;
import com.baeldung.hexagonal.service.DefaultUserService;
import com.baeldung.hexagonal.service.UserService;

public class LoginDemoApp {

    public static void main(String[] args) {

        UserRepository userRepository = new InMemoryUserRepository();
        UserService userService = new DefaultUserService(userRepository);
        UserInterface userInterface = new DefaultUserInterface(userService);

        // This fails as the user credential does not exists
        String loginFailure = userInterface.loginUser("Tom", "123456");
        System.out.println(loginFailure);

        // Registering a new user successfully
        String registerSuccess = userInterface.registerUser("Tom", "123456");
        System.out.println(registerSuccess);

        // Login success as the user credential exists.
        String loginSuccess = userInterface.loginUser("Tom", "123456");
        System.out.println(loginSuccess);
    }
}

package com.baeldung.hexagonalarchitecture.adapter;

import java.util.Scanner;

import com.baeldung.hexagonalarchitecture.core.UserManagerApp;
import com.baeldung.hexagonalarchitecture.port.IAuthentication;

public class ConsoleAuthentication implements IAuthentication {
    UserManagerApp userManagerApp;
    Scanner consoleInput;

    public ConsoleAuthentication(UserManagerApp userManagerApp) {
        this.userManagerApp = userManagerApp;
        this.consoleInput = new Scanner(System.in);
    }

    @Override
    public void login() {
        System.out.print("Username: ");
        String usernameInput = consoleInput.next();

        System.out.print("Password: ");
        String passwordInput = consoleInput.next();

        userManagerApp.login(usernameInput, passwordInput);
    }

    @Override
    public void register() {
        System.out.print("Username: ");
        String usernameInput = consoleInput.next();

        System.out.print("Password: ");
        String passwordInput = consoleInput.next();

        System.out.print("Email: ");
        String emailInput = consoleInput.next();

        userManagerApp.register(usernameInput, passwordInput, emailInput);
    }
}

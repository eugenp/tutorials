package com.baeldung.hexagonalarchitecture.adapter;

import java.util.Scanner;

import com.baeldung.hexagonalarchitecture.port.IAuthentication;

public class ConsoleAuthentication {
    IAuthentication authentication;
    Scanner consoleInput;

    public ConsoleAuthentication(IAuthentication authenticationApp) {
        this.authentication = authenticationApp;
        this.consoleInput = new Scanner(System.in);
    }

    public void login() {
        System.out.print("Username: ");
        String usernameInput = consoleInput.next();

        System.out.print("Password: ");
        String passwordInput = consoleInput.next();

        authentication.login(usernameInput, passwordInput);
    }

    public void register() {
        System.out.print("Username: ");
        String usernameInput = consoleInput.next();

        System.out.print("Password: ");
        String passwordInput = consoleInput.next();

        System.out.print("Email: ");
        String emailInput = consoleInput.next();

        authentication.register(usernameInput, passwordInput, emailInput);
    }
}

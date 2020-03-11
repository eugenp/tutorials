package com.baeldung.hexagonalarchitecture;

import com.baeldung.hexagonalarchitecture.adapter.ConsoleAuthentication;
import com.baeldung.hexagonalarchitecture.adapter.ConsoleNotification;
import com.baeldung.hexagonalarchitecture.adapter.SimpleUserRepository;
import com.baeldung.hexagonalarchitecture.core.UserManagerApp;

public class Main {
    public static void main(String[] args) {
        UserManagerApp userManagerApp = new UserManagerApp(new ConsoleNotification(), new SimpleUserRepository());
        ConsoleAuthentication authentication = new ConsoleAuthentication(userManagerApp);

        authentication.register();
        authentication.login();
    }
}

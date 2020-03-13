package com.baeldung.hexagonalarchitecture;

import com.baeldung.hexagonalarchitecture.adapter.ConsoleAuthentication;
import com.baeldung.hexagonalarchitecture.adapter.ConsoleNotification;
import com.baeldung.hexagonalarchitecture.adapter.SimpleUserRepository;
import com.baeldung.hexagonalarchitecture.core.AuthenticationApp;
import com.baeldung.hexagonalarchitecture.port.IAuthentication;

public class Main {
    public static void main(String[] args) {
        IAuthentication authenticationApp = new AuthenticationApp(new ConsoleNotification(), new SimpleUserRepository());
        ConsoleAuthentication authentication = new ConsoleAuthentication(authenticationApp);

        authentication.register();
        authentication.login();
    }
}

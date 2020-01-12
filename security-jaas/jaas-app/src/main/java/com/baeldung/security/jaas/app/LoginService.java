package com.baeldung.security.jaas.app;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class LoginService {

    public Subject login() throws LoginException {
        LoginContext loginContext = new LoginContext("jaasApplication", new ConsoleCallbackHandler());
        loginContext.login();
        return loginContext.getSubject();
    }
}

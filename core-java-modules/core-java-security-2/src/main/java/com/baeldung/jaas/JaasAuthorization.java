package com.baeldung.jaas;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import java.security.PrivilegedAction;

public class JaasAuthorization {

    public static void main(String[] args) throws LoginException {

        LoginService loginService = new LoginService();
        Subject subject = loginService.login();

        PrivilegedAction privilegedAction = new ResourceAction();
        Subject.doAsPrivileged(subject, privilegedAction, null);
    }
}

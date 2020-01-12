package com.baeldung.security.jaas.app;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

public class JaasAuthentication {

    public static void main(String[] args) throws LoginException {
        LoginService loginService = new LoginService();
        Subject subject = loginService.login();
        System.out.println(subject.getPrincipals().iterator().next() + " sucessfully logeed in");
    }
}

package com.baeldung.jaas.loginmodule;

import com.sun.security.auth.UserPrincipal;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

public class InMemoryLoginModule implements LoginModule {

    private static final String USERNAME = "testuser";
    private static final String PASSWORD = "testpassword";

    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map<String, ?> sharedState;
    private Map<String, ?> options;

    private String username;
    private boolean loginSucceeded = false;
    private Principal userPrincipal;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
                           Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;
    }

    @Override
    public boolean login() throws LoginException {
        NameCallback nameCallback = new NameCallback("username: ");
        PasswordCallback passwordCallback = new PasswordCallback("password: ", false);
        try {
            callbackHandler.handle(new Callback[]{nameCallback, passwordCallback});
            username = nameCallback.getName();
            String password = new String(passwordCallback.getPassword());
            if (USERNAME.equals(username) && PASSWORD.equals(password)) {
                loginSucceeded = true;
            }
        } catch (IOException | UnsupportedCallbackException e) {
            throw new LoginException("Can't login");
        }
        return loginSucceeded;
    }

    @Override
    public boolean commit() throws LoginException {
        if (!loginSucceeded) {
            return false;
        }
        userPrincipal = new UserPrincipal(username);
        subject.getPrincipals().add(userPrincipal);
        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        logout();
        return true;
    }

    @Override
    public boolean logout() throws LoginException {
        subject.getPrincipals().remove(userPrincipal);
        return false;
    }
}

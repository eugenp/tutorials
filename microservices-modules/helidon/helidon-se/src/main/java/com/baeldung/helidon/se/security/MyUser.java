package com.baeldung.helidon.se.security;

import io.helidon.security.providers.httpauth.SecureUserStore;

import java.util.Arrays;
import java.util.Collection;

public class MyUser implements SecureUserStore.User {

    private String login;
    private char[] password;
    private Collection<String> roles;

    public MyUser(String login, char[] password, Collection<String> roles) {
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public String login() {
        return login;
    }

    @Override
    public boolean isPasswordValid(char[] chars) {
        return Arrays.equals(chars, password);
    }

    @Override
    public Collection<String> roles() {
        return roles;
    }
}

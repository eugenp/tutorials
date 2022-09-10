package com.baeldung.helidon.se.security;

import io.helidon.security.provider.httpauth.UserStore;

import java.util.Collection;

public class MyUser implements UserStore.User {

    private String login;
    private char[] password;
    private Collection<String> roles;

    public MyUser(String login, char[] password, Collection<String> roles) {
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public char[] getPassword() {
        return password;
    }

    @Override
    public Collection<String> getRoles() {
        return roles;
    }
}

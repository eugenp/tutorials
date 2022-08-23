package com.baeldung.javaee.security;

import java.util.ArrayList;
import java.util.List;

public class UserDetail {
    private String uid;
    private String login;
    private String password;
    private List<String> roles = new ArrayList<>();
    //...

    UserDetail(String uid, String login, String password) {
        this.uid = uid;
        this.login = login;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void addRole(String role) {
        roles.add(role);
    }
}
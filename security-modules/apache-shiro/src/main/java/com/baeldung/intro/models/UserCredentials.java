package com.baeldung.intro.models;

public class UserCredentials {

    private String username;
    private String password;
    private boolean rememberMe = false;

    public UserCredentials() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public String toString() {
        return "username = " + getUsername()
                + "\nrememberMe = " + isRememberMe();
    }
}

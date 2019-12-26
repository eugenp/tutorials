package com.baeldung.jhipster5.security.dto;

/**
 * Simple DTO representing a login request to a remote service.
 */
public class LoginRequest {

    private String username;

    private String password;

    public LoginRequest() {
    }

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
}

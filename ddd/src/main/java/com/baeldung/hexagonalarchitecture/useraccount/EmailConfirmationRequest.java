package com.baeldung.hexagonalarchitecture.useraccount;

import javax.validation.constraints.NotBlank;

/**
 * @author Fact S Musingarimi
 * 23/2/2020
 * 13:32
 */
public class EmailConfirmationRequest {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Token is required")
    private String token;
    @NotBlank(message = "Log in url is required")
    private String logInUrl;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogInUrl() {
        return logInUrl;
    }

    public void setLogInUrl(String logInUrl) {
        this.logInUrl = logInUrl;
    }

    @Override
    public String toString() {
        return "UserRegistrationEmailConfirmationRequest{" +
                "username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", logInUrl='" + logInUrl + '\'' +
                '}';
    }
}

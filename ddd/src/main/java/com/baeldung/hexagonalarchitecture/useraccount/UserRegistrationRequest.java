package com.baeldung.hexagonalarchitecture.useraccount;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author Fact S Musingarimi
 * 23/2/2020
 * 13:20
 */
public class UserRegistrationRequest {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "An email is required")
    @Email(message = "A valid email is required")
    private String email;
    @NotBlank(message = "Log in url is required")
    private String logInUrl;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogInUrl() {
        return logInUrl;
    }

    public void setLogInUrl(String logInUrl) {
        this.logInUrl = logInUrl;
    }

    @Override
    public String toString() {
        return "UserRegistrationRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", logInUrl='" + logInUrl + '\'' +
                '}';
    }
}

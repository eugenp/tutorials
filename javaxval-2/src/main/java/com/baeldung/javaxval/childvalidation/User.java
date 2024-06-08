package com.baeldung.javaxval.childvalidation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class User {

    @NotBlank(message = "User name must be present")
    @Size(min = 3, max = 50, message = "User name size not valid")
    private String name;

    @NotBlank(message = "User email must be present")
    @Email(message = "User email format is incorrect")
    private String email;

    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}


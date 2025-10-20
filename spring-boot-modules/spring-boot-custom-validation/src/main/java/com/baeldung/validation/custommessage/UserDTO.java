package com.baeldung.validation.custommessage;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class UserDTO {

    @NotBlank(message = "{user.name.notblank}")
    private String name;

    @Email(message = "{user.email.invalid}")
    private String email;

    @Min(value = 18, message = "{user.age.min}")
    private int age;

    // Getters and setters
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
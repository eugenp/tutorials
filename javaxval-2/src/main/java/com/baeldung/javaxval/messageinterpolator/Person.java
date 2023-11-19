package com.baeldung.javaxval.messageinterpolator;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class Person {

    @Size(min = 10, max = 100, message = "Name should be between {min} and {max} characters")
    private String name;

    @Min(value = 18, message = "Age should not be less than {value}")
    private int age;
    
    @Email(message = "Email address should be in a correct format: ${validatedValue}")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

}

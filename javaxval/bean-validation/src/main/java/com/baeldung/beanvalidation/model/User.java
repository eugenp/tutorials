package com.baeldung.beanvalidation.model;

import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;

public class User implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters")
    private String name;
    
    @Email(message = "Email must be a well-formed email address")
    private String email;
    
    @Min(value = 1, message = "Age must not be lesser than 1")
    @Max(value = 99, message = "Age must not be greater than 99")
    private int age;
    
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

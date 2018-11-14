package org.baeldung.javabeanconstraints.entities;

import javax.validation.constraints.NotBlank;

public class UserNotBlank {

    @NotBlank(message = "Name is mandatory")
    private final String name;
    
    public UserNotBlank(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return "User{" + "name=" + name + "}";
    }
}

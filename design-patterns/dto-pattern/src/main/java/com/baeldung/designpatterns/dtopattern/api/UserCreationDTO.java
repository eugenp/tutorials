package com.baeldung.designpatterns.dtopattern.api;

import java.util.List;

public class UserCreationDTO {

    private String name;
    private String password;
    private List<String> roles;

    UserCreationDTO() {}

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
    }

    void setName(String name) {
        this.name = name;
    }

    void setPassword(String password) {
        this.password = password;
    }

    void setRoles(List<String> roles) {
        this.roles = roles;
    }
}

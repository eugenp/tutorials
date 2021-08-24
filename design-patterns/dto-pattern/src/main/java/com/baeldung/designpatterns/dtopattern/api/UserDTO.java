package com.baeldung.designpatterns.dtopattern.api;

import java.util.List;

public class UserDTO {
    private String name;
    private List<String> roles;

    public UserDTO(String name, List<String> roles) {
        this.name = name;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public List<String> getRoles() {
        return roles;
    }

}

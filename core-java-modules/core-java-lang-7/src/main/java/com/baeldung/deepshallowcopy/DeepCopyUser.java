package com.baeldung.deepshallowcopy;

import java.util.ArrayList;
import java.util.List;

public class DeepCopyUser {
    private String name;
    private List<String> roles;

    public DeepCopyUser(String name, List<String> roles) {
        this.name = name;
        this.roles = new ArrayList<>(roles);
    }

    // Copy constructor for deep copy
    public DeepCopyUser(DeepCopyUser user) {
        this.name = user.name;
        this.roles = new ArrayList<>(user.roles);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}

package com.baeldung.deepshallowcopy;

import java.util.ArrayList;
import java.util.List;

public class ShallowCopyUser implements Cloneable {

    private String name;
    private List<String> roles;

    public ShallowCopyUser(String name, List<String> roles) {
        this.name = name;
        this.roles = new ArrayList<>(roles);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); // Performs a shallow copy
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

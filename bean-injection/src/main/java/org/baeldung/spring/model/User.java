package org.baeldung.spring.model;


public class User {

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    private UserDetails details;

    private UserAddress address;

    public UserAddress getAddress() {
        return address;
    }

    public UserDetails getDetails() {
        return details;
    }

    public void setAddress(UserAddress address) {
        this.address = address;
    }

    public void setDetails(UserDetails details) {
        this.details = details;
    }
}

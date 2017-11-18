package com.stackify.optional.chaining;

import java.util.Optional;

public class User {
    private String email;
    private String password;

    private Address address;

    private String position;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Optional<Address> getAddress() {
        return Optional.ofNullable(address);
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Optional<String> getPosition() {
        return Optional.ofNullable(position);
    }

    public void setPosition(String position) {
        this.position = position;
    }

}

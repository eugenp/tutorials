package com.baeldung.java_8_features;

import java.util.Optional;

public class User {

    private String name;

    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public static boolean isRealUser(User user) {
        return true;
    }

    public String getOrThrow() {
        String value = null;
        Optional<String> valueOpt = Optional.ofNullable(value);
        String result = valueOpt.orElseThrow(CustomException::new).toUpperCase();
        return result;
    }

    public boolean isLegalName(String name) {
        return name.length() > 3 && name.length() < 16;
    }
}

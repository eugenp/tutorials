package com.baeldung.lapsedlistener;

public class User implements Observer {

    private final String name;
    private final String email;
    private final String phone;
    private final String street;
    private final String city;
    private final String state;
    private final String zipCode;

    public User(String name, String email, String phone, String street, String city, String state, String zipCode) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    @Override
    public void update(final String quote) {
        // user got updated with a new quote
    }

    @Override
    public void subscribe(final Subject subject) {
        subject.attach(this);
    }

    @Override
    public void unsubscribe(final Subject subject) {
        subject.detach(this);
    }

    @Override
    public String toString() {
        return "User [name=" + name + "]";
    }
}

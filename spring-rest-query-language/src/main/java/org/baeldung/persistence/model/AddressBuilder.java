package org.baeldung.persistence.model;

import java.time.LocalDate;

public final class AddressBuilder {
    private long id;
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private boolean checked;
    private LocalDate dateOfOccupation;
    private User user;

    private AddressBuilder() {
    }

    public static AddressBuilder anAddress() {
        return new AddressBuilder();
    }

    public AddressBuilder id(long id) {
        this.id = id;
        return this;
    }

    public AddressBuilder street(String street) {
        this.street = street;
        return this;
    }

    public AddressBuilder city(String city) {
        this.city = city;
        return this;
    }

    public AddressBuilder state(String state) {
        this.state = state;
        return this;
    }

    public AddressBuilder zipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public AddressBuilder checked(boolean checked) {
        this.checked = checked;
        return this;
    }

    public AddressBuilder dateOfOccupation(LocalDate dateOfOccupation) {
        this.dateOfOccupation = dateOfOccupation;
        return this;
    }

    public AddressBuilder user(User user) {
        this.user = user;
        return this;
    }

    public AddressBuilder but() {
        return anAddress().id(id).street(street).city(city).state(state).zipcode(zipcode).checked(checked).dateOfOccupation(dateOfOccupation).user(user);
    }

    public Address build() {
        Address address = new Address();
        address.setId(id);
        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        address.setZipcode(zipcode);
        address.setChecked(checked);
        address.setDateOfOccupation(dateOfOccupation);
        address.setUser(user);
        return address;
    }
}

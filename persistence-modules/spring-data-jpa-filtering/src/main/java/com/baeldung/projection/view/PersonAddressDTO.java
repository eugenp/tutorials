package com.baeldung.projection.view;

public class PersonAddressDTO {

    private final String firstName;
    private String city;

    public PersonAddressDTO(String firstName, String city) {
        this.firstName = firstName;
        this.city = city;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getCity() {
        return city;
    }

}

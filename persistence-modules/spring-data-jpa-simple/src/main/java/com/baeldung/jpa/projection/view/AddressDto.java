package com.baeldung.jpa.projection.view;

public class AddressDto {

    private final String zipCode;
    private final PersonDto person;

    public AddressDto(String zipCode, PersonDto person) {
        this.zipCode = zipCode;
        this.person = person;
    }

    public String getZipCode() {
        return zipCode;
    }

    public PersonDto getPerson() {
        return person;
    }
}

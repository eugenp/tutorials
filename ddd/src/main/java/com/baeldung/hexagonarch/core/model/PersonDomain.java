package com.baeldung.hexagonarch.core.model;

import org.hexagonarch.ports.driven.Person;

public class PersonDomain {

    public PersonDomain() {
        // empty constructor
    }

    public PersonDomain(String name, String streetName, String city, String zipCode) {
        this.name = name;
        this.streetName = streetName;
        this.city = city;
        this.zipCode = zipCode;
    }

    private String name;
    private String streetName;
    private String city;
    private String zipCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static PersonDomain fromPerson(Person p) {
        PersonDomain personDomain = new PersonDomain();
        personDomain.setName(p.name());
        personDomain.setStreetName(p.streetName());
        personDomain.setCity(p.city());
        personDomain.setZipCode(p.zipCode());
        return personDomain;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}

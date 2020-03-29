package com.baeldung.dto;

public class CustomerDto {

    private String forename;
    private String surname;

    public String getForename() {
        return forename;
    }

    public CustomerDto setForename(String forename) {
        this.forename = forename;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public CustomerDto setSurname(String surname) {
        this.surname = surname;
        return this;
    }
}

package com.baeldung.hexagonal_example.domain;

public class ContactDTO {

    private String name;
    private String email;
    private String mobilePhone;

    ContactDTO(String name, String email, String mobilePhone) {
        this.name = name;
        this.email = email;
        this.mobilePhone = mobilePhone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

}

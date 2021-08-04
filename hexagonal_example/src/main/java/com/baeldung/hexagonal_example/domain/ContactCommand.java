package com.baeldung.hexagonal_example.domain;

public class ContactCommand {

    public static ContactCommand of(String name, String email, String mobilePhone) {
        return new ContactCommand(name, email, mobilePhone);
    }

    private String name;
    private String email;
    private String mobilePhone;

    ContactCommand(String name, String email, String mobilePhone) {
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

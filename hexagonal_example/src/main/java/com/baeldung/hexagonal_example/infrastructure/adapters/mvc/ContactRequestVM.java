package com.baeldung.hexagonal_example.infrastructure.adapters.mvc;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;

public class ContactRequestVM {

    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String mobilePhone;

    @JsonCreator
    public ContactRequestVM(String name, String email, String mobilePhone) {
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

package com.example.domain;

import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter

public class Customer {
    private CustomerId id;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phone;

    public Customer(CustomerId id, String firstName, String lastName, String address, String email, String phone) {
        validateName(firstName, lastName);
        validateAddress(address);
        validateEmail(email);
        validatePhone(phone);

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    private void validatePhone(String phone) {
        if (StringUtils.isEmpty(phone))
            throw new BadDataException("Invalid Phone");
    }

    private void validateEmail(String email) {
        if (StringUtils.isEmpty(email))
            throw new BadDataException("Invalid Email");
    }

    private void validateAddress(String address) {
        if (StringUtils.isEmpty(address))
            throw new BadDataException("Invalid Address");
    }

    private void validateName(String firstName, String lastName) {
        if (StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName))
            throw new BadDataException("Invalid Name");
    }
}

package com.baeldung.hexagonal_example.domain;

import java.util.Objects;
import java.util.regex.Pattern;

class Contact {

    private static final String PHONE_MASK = "\\+\\d{1,3}\\(\\d{3}\\)\\d{3}-\\d{4}";
    private static final String EMAIL_MASK = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\\\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\\\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    private String name;
    private String email;
    private String mobilePhone;

    public Contact(String name, String email, String mobilePhone) {
        setFields(name, email, mobilePhone);
        this.validateEmail();
        this.validateMobilePhone();
    }

    private void setFields(String name, String email, String mobilePhone) {
        try {
            this.name = Objects.requireNonNull(name);
            this.email = Objects.requireNonNull(email);
            this.mobilePhone = Objects.requireNonNull(mobilePhone);
        } catch (NullPointerException ex) {
            throw new DomainException("The properties name, email and mobile phone are requited");
        }

    }

    private void validateMobilePhone() {
        if (!Pattern.matches(PHONE_MASK, mobilePhone)) {
            throw new DomainException("Invalid format, phone should be: +999(999)999-9999");
        }
    }

    private void validateEmail() {
        if (!Pattern.matches(EMAIL_MASK, email)) {
            throw new DomainException("Invalid email");
        }
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

    public ContactDTO toDTO() {
        return new ContactDTO(name, email, mobilePhone);
    }
}

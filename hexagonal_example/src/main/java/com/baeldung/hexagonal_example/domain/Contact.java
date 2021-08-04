package com.baeldung.hexagonal_example.domain;

class Contact {

    private String name;
    private String email;
    private String mobilePhone;

    public Contact(String name, String email, String mobilePhone) {
        this.name = name;
        this.email = email;
        this.mobilePhone = mobilePhone;
        this.validateEmail();
        this.validateMobilePhone();
    }

    private void validateMobilePhone() {
        // validate or throws DomainException
    }

    private void validateEmail() {
        // validate or throws DomainException
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

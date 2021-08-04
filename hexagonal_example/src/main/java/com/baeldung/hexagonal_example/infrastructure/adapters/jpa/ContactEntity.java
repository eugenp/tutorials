package com.baeldung.hexagonal_example.infrastructure.adapters.jpa;

import com.baeldung.hexagonal_example.domain.ContactDTO;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class ContactEntity {

    static ContactEntity from(ContactDTO contactDTO) {
        return new ContactEntity(contactDTO.getName(), contactDTO.getEmail(), contactDTO.getMobilePhone());
    }

    @Id
    private String name;
    private String email;
    private String mobilePhone;

    protected ContactEntity() {
        // Hibernate
    }

    public ContactEntity(String name, String email, String mobilePhone) {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}

package com.baeldung.dynamicvalidation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import com.baeldung.dynamicvalidation.ContactInfo;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ContactInfo
    @NotNull
    private String contactInfo;

    public Customer() {
    }

    public Customer(final long id, final String contactInfo) {
        this.id = id;
        this.contactInfo = contactInfo;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(final String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

}

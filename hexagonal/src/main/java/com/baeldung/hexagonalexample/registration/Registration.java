package com.baeldung.hexagonalexample.registration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String emailAddress;

    Registration() {

    }

    Registration(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Long getId() {
        return id;
    }

    public Registration setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Registration setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }
}

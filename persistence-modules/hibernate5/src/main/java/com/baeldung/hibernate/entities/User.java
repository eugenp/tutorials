package com.baeldung.hibernate.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

@Entity
public class User {

    @Column(length = 3)
    private String firstName;

    @Size(min = 3, max = 15)
    private String middleName;

    @Length(min = 3, max = 15)
    private String lastName;

    public User(String firstName, String middleName, String lastName) {
        super();
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middletName) {
        this.middleName = middletName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}

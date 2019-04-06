package com.baeldung.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class Passenger {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(nullable = false)
    private String firstName;

    @Basic(optional = false)
    @Column(nullable = false)
    private String lastName;

    private Passenger(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    static Passenger from(String firstName, String lastName) {
        return new Passenger(firstName, lastName);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Passenger{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + '}';
    }
}

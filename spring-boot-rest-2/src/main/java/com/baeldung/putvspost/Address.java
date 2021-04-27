package com.baeldung.putvspost;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Address {

    private @Id @GeneratedValue Long id;
    private String name;
    private String city;
    private String pin;

    Address() {
    };

    public Address(String name, String city, String pin) {
        this.name = name;
        this.city = city;
        this.pin = pin;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "Address [id=" + id + ", name=" + name + ", city=" + city + ", pin=" + pin + "]";
    }

}

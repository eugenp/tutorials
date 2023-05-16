package com.baeldung.hibernate.multitenancy;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "Car")
@Table(name = "Car")
public class Car implements Serializable {

    private static final long serialVersionUID = 1015320564683423342L;

    private String brand;

    @Id
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

}

package com.baeldung.hibernate.multitenancy;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

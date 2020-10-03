package com.baeldung.domain.entities;

import javax.persistence.*;

@Entity
public class Car {

    @Id
    private String vin;     // vehicle identification number
    private String brand;
    private Short modelYear;

    public Car() { }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Short getModelYear() {
        return modelYear;
    }

    public void setModelYear(Short modelYear) {
        this.modelYear = modelYear;
    }
}

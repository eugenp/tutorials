package com.baeldung.exists;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author paullatzelsperger
 * @since 2019-03-20
 */
@Entity
public class Car {

    @Id
    @GeneratedValue
    private int id;
    private Integer power;
    private String model;

    Car() {

    }

    public Car(int power, String model) {
        this.power = power;
        this.model = model;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getId() {
        return id;
    }
}

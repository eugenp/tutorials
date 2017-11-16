package com.baeldung.hibernate.pojo.inheritance;

import javax.persistence.Entity;

@Entity
public class Car extends Vehicle {
    private String engine;

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

}

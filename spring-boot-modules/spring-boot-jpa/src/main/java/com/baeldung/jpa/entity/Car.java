package com.baeldung.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String model;

    @Column
    private Integer year;

    public long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public Integer getYear() {
        return year;
    }

}

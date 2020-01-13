package com.baeldung.dddhexagonalquickexample.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Car {

    @Id
    private Long id;
    private String model;
    private Double price;

    @ManyToOne
    @JoinColumn(name="idStore")
    @JsonIgnore
    private CarStore store;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }	
    public CarStore getStore() {
        return store;
    }
    public void setStore(CarStore store) {
        this.store = store;
    }
}

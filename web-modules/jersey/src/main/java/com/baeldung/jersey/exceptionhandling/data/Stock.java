package com.baeldung.jersey.exceptionhandling.data;

import com.baeldung.jersey.exceptionhandling.repo.Identifiable;

public class Stock implements Identifiable {
    private static final long serialVersionUID = 1L;
    private String id;
    private Double price;

    public Stock() {
    }

    public Stock(String id, Double price) {
        this.id = id;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

package com.baeldung.spring.aotrepository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PRODUCTS")
public class Product {

    @Id
    private Long id;
    private String name;
    private double price;

    public Product() {
        super();
    }

    private Product(Long id, String name, double price) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product [name=" + name + ", id=" + id + ", price=" + price + "]";
    }
}

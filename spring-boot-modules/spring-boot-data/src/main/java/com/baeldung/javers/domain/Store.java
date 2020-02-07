package com.baeldung.javers.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Store {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    @Embedded
    private Address address;
    @OneToMany(
            mappedBy = "store",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Product> products = new ArrayList<>();

    public Store(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Store() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addProduct(Product product) {
        product.setStore(this);
        this.products.add(product);
    }

    public List<Product> getProducts() {
        return this.products;
    }
}

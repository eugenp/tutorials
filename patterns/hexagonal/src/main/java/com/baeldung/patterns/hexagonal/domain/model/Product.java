package com.baeldung.patterns.hexagonal.domain.model;

import java.util.Objects;

import static java.util.Objects.hash;

public class Product {
    private String id;
    private String name;

    public Product(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Product)) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }
        Product product = (Product) object;
        return Objects.equals(id, product.id) &&
               Objects.equals(name, product.name);
    }

    public int hashCode() {
        return hash(super.hashCode(), id, name);
    }

    @Override
    public java.lang.String toString() {
        return "Product{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               '}';
    }
}

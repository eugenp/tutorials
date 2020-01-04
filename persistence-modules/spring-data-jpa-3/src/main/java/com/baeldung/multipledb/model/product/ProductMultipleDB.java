package com.baeldung.multipledb.model.product;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "products")
public class ProductMultipleDB {

    @Id
    private int id;

    private String name;

    private double price;

    public ProductMultipleDB() {
        super();
    }

    private ProductMultipleDB(int id, String name, double price) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static ProductMultipleDB from(int id, String name, double price) {
        return new ProductMultipleDB(id, name, price);
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
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
        final StringBuilder builder = new StringBuilder();
        builder.append("Product [name=")
            .append(name)
            .append(", id=")
            .append(id)
            .append("]");
        return builder.toString();
    }
}

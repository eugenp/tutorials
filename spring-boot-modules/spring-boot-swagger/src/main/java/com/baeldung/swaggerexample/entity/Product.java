package com.baeldung.swaggerexample.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

public class Product implements Serializable {

    @Schema(name = "Product ID", example = "1", required = true)
    private Long id;
    @Schema(name = "Product name", example = "Product 1", required = false)
    private String name;
    @Schema(name = "Product price", example = "$100.00", required = true)
    private String price;

    // constructor and getter/setters

    public Product(long id, String name, String price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

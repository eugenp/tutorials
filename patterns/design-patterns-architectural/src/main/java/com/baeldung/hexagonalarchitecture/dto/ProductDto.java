package com.baeldung.hexagonalarchitecture.dto;

import com.baeldung.hexagonalarchitecture.domain.Product;

public class ProductDto {
    private String name;
    private String description;
    private Double price;
    private String type;
    private String manufacturer;

    public ProductDto(Product product){
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.type = product.getType();
        this.manufacturer = product.getManufacturer();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}

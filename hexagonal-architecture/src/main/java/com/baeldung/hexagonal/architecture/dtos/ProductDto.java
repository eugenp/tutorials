package com.baeldung.hexagonal.architecture.dtos;

import com.baeldung.hexagonal.architecture.model.Product;

/**
 * @author AshwiniKeshri
 *
 */
public class ProductDto {

    private Long id;

    private String name;

    private Long quantity;

    private Double price;

    private String description;

    public ProductDto() {
    }

    public ProductDto(Product product) {
        this.description = product.getDescription();
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

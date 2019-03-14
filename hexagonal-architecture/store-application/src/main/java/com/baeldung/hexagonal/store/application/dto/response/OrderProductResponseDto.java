package com.baeldung.hexagonal.store.application.dto.response;

public class OrderProductResponseDto {

    private Integer quantity;
    private ProductResponseDto product;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductResponseDto getProduct() {
        return product;
    }

    public void setProduct(ProductResponseDto product) {
        this.product = product;
    }
}

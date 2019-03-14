package com.baeldung.hexagonal.store.application.dto.response;

import java.util.List;

public class OrderResponseDto {
    private String status;
    private Long id;
    private List<OrderProductResponseDto> orderProducts;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderProductResponseDto> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProductResponseDto> orderProducts) {
        this.orderProducts = orderProducts;
    }
}

package com.github.lihongjie.model;

public class BillingForm {

    private Long id;

    private String name;

    private String description;

    private Double totalPrice;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BillingRequest toBillingRequest() {
        BillingRequest.Builder request = new BillingRequest.Builder();
        return request.name(name)
                .description(description)
                .totalPrice(totalPrice)
                .build();
    }
}

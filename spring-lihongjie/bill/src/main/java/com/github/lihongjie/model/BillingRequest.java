package com.github.lihongjie.model;

import java.util.Date;

public class BillingRequest {

    private Long id;

    private String name;

    private Double totalPrice;

    private String description;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public String getDescription() {
        return description;
    }

    public static class Builder {

        private Long id;

        private String name;

        private Double toalPrice;

        private String description;

        private Date createdAt;

        private Long createdBy;

        private Date lastEditAt;

        private Long lastEditBy;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder totalPrice(Double price) {
            this.toalPrice = price;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public BillingRequest build() {
            BillingRequest request = new BillingRequest();
            request.id = id;
            request.name = name;
            request.totalPrice = toalPrice;
            request.description = description;
            return request;
        }
    }
}

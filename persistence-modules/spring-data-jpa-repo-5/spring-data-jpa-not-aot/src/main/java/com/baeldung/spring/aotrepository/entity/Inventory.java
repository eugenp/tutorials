package com.baeldung.spring.aotrepository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "INVENTORY")
public class Inventory {

    @Id
    private Long productId;
    private Long balance;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Order [productId=" + productId + ", balance=" + balance + "]";
    }
}

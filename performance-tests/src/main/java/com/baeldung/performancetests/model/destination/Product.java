package com.baeldung.performancetests.model.destination;

import com.google.common.base.Objects;
import com.googlecode.jmapper.annotations.JGlobalMap;

import java.math.BigDecimal;

@JGlobalMap
public class Product {
    private BigDecimal price;
    private int quantity;

    public Product() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public RefundPolicy getRefundPolicy() {
        return refundPolicy;
    }

    public void setRefundPolicy(RefundPolicy refundPolicy) {
        this.refundPolicy = refundPolicy;
    }

    private String name;

    public Product(BigDecimal price, int quantity, String name, String description, boolean available, RefundPolicy refundPolicy) {
        this.price = price;
        this.quantity = quantity;
        this.name = name;
        this.description = description;
        this.available = available;
        this.refundPolicy = refundPolicy;
    }

    String description;
    boolean available;
    private RefundPolicy refundPolicy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o.getClass() == com.baeldung.performancetests.model.source.Product.class) {
            com.baeldung.performancetests.model.source.Product product =
              (com.baeldung.performancetests.model.source.Product) o;
            return quantity == product.getQuantity() &&
              available == product.isAvailable() &&
              Objects.equal(price, product.getPrice()) &&
              Objects.equal(name, product.getName()) &&
              Objects.equal(description, product.getDescription()) &&
              Objects.equal(refundPolicy, product.getRefundPolicy());
        }
        if(o.getClass() != getClass()) return false;
        Product product = (Product) o;
        return quantity == product.quantity &&
          available == product.available &&
          Objects.equal(price, product.price) &&
          Objects.equal(name, product.name) &&
          Objects.equal(description, product.description) &&
          Objects.equal(refundPolicy, product.refundPolicy);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(price, quantity, name, description, available, refundPolicy);
    }
}

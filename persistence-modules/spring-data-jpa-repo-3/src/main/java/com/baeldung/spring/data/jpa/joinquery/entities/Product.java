package com.baeldung.spring.data.jpa.joinquery.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Product {
    public Product(){}
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Product(Long id, String productName, Double price, CustomerOrder customerOrder) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.customerOrder = customerOrder;
    }

    @Column
    private String productName;

    @Column
    private Double price;

    @Override
    public String toString() {
        return "Dispense{" +
          "id=" + id +
          ", productName='" + productName + '\'' +
          ", price=" + price +
          '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product dispense = (Product) o;
        return Objects.equals(id, dispense.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder co) {
        this.customerOrder = co;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customerorder_id", nullable = false)
    private CustomerOrder customerOrder;
}

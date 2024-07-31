package com.baeldung.spring.data.jpa.joinquery.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
public class CustomerOrder {
    public CustomerOrder(){}
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @OneToMany(mappedBy = "customerOrder"
    , cascade = CascadeType.ALL)
    private Set<Product> products;

    @Column
    private LocalDate orderDate;

    public CustomerOrder(Long id, Set<Product> products, LocalDate orderDate, Customer customer) {
        this.id = id;
        this.products = products;
        this.orderDate = orderDate;
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Consult{" +
          "id=" + id +
          ", products=" + products +
          ", orderDate=" + orderDate +
          ", customer=" + customer +
          '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerOrder co = (CustomerOrder) o;
        return Objects.equals(id, co.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_id", nullable = false)
    private Customer customer;

    public Long getId() {
        return id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

package com.baeldung.hexagonal.store.core.context.order.entity;

import com.baeldung.hexagonal.store.core.context.customer.entity.Customer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    private String status;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @OneToMany(mappedBy = "id.order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @Transient
    public Double getTotalOrderPrice() {
        return getOrderProducts()
                .stream()
                .reduce(
                        0D,
                        (aDouble, orderProduct) -> orderProduct.getTotalPrice(),
                        (aDouble, aDouble2) -> aDouble + aDouble2);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public void addOrderProduct(OrderProduct product) {
        this.orderProducts.add(product);
    }

    @Transient
    public int getNumberOfProducts() {
        return this.orderProducts.size();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

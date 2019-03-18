package com.baeldung.hexagonal.store.core.context.order.entity;

import com.baeldung.hexagonal.store.core.context.customer.entity.Customer;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String status;

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

    public void addOrderProduct(OrderProduct product) {
        this.orderProducts.add(product);
    }

    @Transient
    public int getNumberOfProducts() {
        return this.orderProducts.size();
    }
}

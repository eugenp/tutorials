package com.baeldung.ddd.layers.domain;

import com.baeldung.ddd.layers.domain.exception.DomainException;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private final ObjectId id;
    private OrderStatus status;
    private List<Product> products;
    private BigDecimal price;

    public Order(final ObjectId id, final Product product) {
        this.id = id;
        this.products = new ArrayList<>(Collections.singletonList(product));
        this.status = OrderStatus.CREATED;
        this.price = product.getPrice();
    }

    public void complete() {
        validateState();
        this.status = OrderStatus.COMPLETED;
    }

    public void addProduct(final Product product) {
        validateState();
        validateProduct(product);
        products.add(product);
        price = price.add(product.getPrice());
    }

    public void removeProduct(final String name) {
        validateState();
        final Product product = getProduct(name);
        products.remove(product);

        price = price.subtract(product.getPrice());
    }

    private Product getProduct(String name) {
        return products
          .stream()
          .filter(product -> product
            .getName()
            .equals(name))
          .findFirst()
          .orElseThrow(() -> new DomainException("Product with " + name + " doesn't exist."));
    }

    private void validateState() {
        if (OrderStatus.COMPLETED.equals(status)) {
            throw new DomainException("The order is in completed state.");
        }
    }

    private void validateProduct(final Product product) {
        if (product == null) {
            throw new DomainException("The product cannot be null.");
        }
    }

    public ObjectId getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public BigDecimal getPrice() {
        return price;
    }
}

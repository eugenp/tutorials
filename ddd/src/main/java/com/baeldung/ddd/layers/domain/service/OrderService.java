package com.baeldung.ddd.layers.domain.service;

import com.baeldung.ddd.layers.domain.Product;
import org.bson.types.ObjectId;

import java.util.List;

public interface OrderService {
    ObjectId createOrder(List<Product> products);

    void addProduct(ObjectId id, Product product);

    void completeOrder(ObjectId id);

    void deleteProduct(ObjectId id, String name);
}

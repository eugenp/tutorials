package com.baeldung.ddd.layers.domain.service;

import com.baeldung.ddd.layers.domain.Product;
import org.bson.types.ObjectId;

public interface OrderService {
    ObjectId createOrder(final Product product);

    void addProduct(ObjectId id, Product product);

    void completeOrder(ObjectId id);

    void deleteProduct(ObjectId id, String name);
}

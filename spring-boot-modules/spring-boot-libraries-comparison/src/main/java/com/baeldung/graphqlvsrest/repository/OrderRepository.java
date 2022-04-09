package com.baeldung.graphqlvsrest.repository;

import com.baeldung.graphqlvsrest.entity.Order;
import com.baeldung.graphqlvsrest.entity.Product;
import com.baeldung.graphqlvsrest.model.ProductModel;

import java.util.List;

public interface OrderRepository {
    List<Order> getOrdersByProduct(Integer productId);
}

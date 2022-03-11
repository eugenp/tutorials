package com.baeldung.graphqlvsrest.resolver;

import com.baeldung.graphqlvsrest.entity.Order;
import com.baeldung.graphqlvsrest.entity.Product;
import com.baeldung.graphqlvsrest.repository.OrderRepository;
import com.coxautodev.graphql.tools.GraphQLResolver;

import java.util.List;

public class ProductResolver implements GraphQLResolver<Product> {
    private OrderRepository orderRepository;
    public ProductResolver(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }
    public List<Order> getOrders(Product product){
        return orderRepository.getOrdersByProduct(product.getId());
    }
}

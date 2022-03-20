package com.baeldung.graphqlvsrest.resolver;

import com.baeldung.graphqlvsrest.entity.Order;
import com.baeldung.graphqlvsrest.entity.Product;
import com.baeldung.graphqlvsrest.model.AttributeKeyValueModel;
import com.baeldung.graphqlvsrest.repository.OrderRepository;
import com.coxautodev.graphql.tools.GraphQLResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedList;
import java.util.List;

public class ProductResolver implements GraphQLResolver<Product> {
    private OrderRepository orderRepository;
    public ProductResolver(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }
    public List<Order> getOrders(Product product){
        return orderRepository.getOrdersByProduct(product.getId());
    }

    public List<AttributeKeyValueModel> getAttribute_list(Product product){
        List<AttributeKeyValueModel> attributeModelList = new LinkedList<>();
        product.getAttributes().forEach((key, val) -> attributeModelList.add(new AttributeKeyValueModel(key, val)));
        return attributeModelList;
    }

    public String getAttribute_string(Product product){
        try {
            return new ObjectMapper().writeValueAsString(product.getAttributes());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
}

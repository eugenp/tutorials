package com.baeldung.hexagonalarchitectureinjava.domain;

import com.baeldung.hexagonalarchitectureinjava.domain.beans.ConfimrOrderRequest;
import com.baeldung.hexagonalarchitectureinjava.domain.beans.CreateOrderRequest;
import com.baeldung.hexagonalarchitectureinjava.domain.beans.OrderResponse;
import com.baeldung.hexagonalarchitectureinjava.outputport.OrderEntity;
import com.baeldung.hexagonalarchitectureinjava.outputport.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class OrderManagementService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderManagementService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderResponse createOrder(CreateOrderRequest orderRequest) {
        validateOrderRequest(orderRequest);
        OrderEntity orderEntity = processOrder(orderRequest);
        orderRepository.saveOrder(orderEntity);
        return buildOrderResponse(orderEntity.getOrderId());
    }

    private OrderResponse buildOrderResponse(UUID orderId) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(orderId);
        return response;
    }

    private void validateOrderRequest(CreateOrderRequest orderRequest) {
        //Validation logic here
    }

    private OrderEntity processOrder(CreateOrderRequest orderRequest) {
        return orderRequest.getOrderEntity();
    }

    public OrderResponse confirmOrder(UUID id, ConfimrOrderRequest request) {
        OrderEntity orderById = orderRepository.findOrderById(request.getOrderId());
        confirmOrderItem(orderById);
        return buildOrderResponse(orderById.getOrderId());
    }

    private void confirmOrderItem(OrderEntity orderById) {
        //Confirm order logic here
    }

    public OrderResponse cancelOrder(UUID orderId) {
        OrderEntity orderById = orderRepository.findOrderById(orderId);
        cancelOrderItem(orderById);
        return buildOrderResponse(orderById.getOrderId());
    }

    private void cancelOrderItem(OrderEntity orderById) {
        //Cancel order logic here.
    }
}

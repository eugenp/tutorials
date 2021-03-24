package com.baeldung.hexagonalarchitectureinjava.domain;


import com.baeldung.hexagonalarchitectureinjava.domain.beans.ConfimrOrderRequest;
import com.baeldung.hexagonalarchitectureinjava.domain.beans.CreateOrderRequest;
import com.baeldung.hexagonalarchitectureinjava.outputport.OrderEntity;
import com.baeldung.hexagonalarchitectureinjava.outputport.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderManagementServiceUnitTest {

    private OrderManagementService orderManagementService;

    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderManagementService = new OrderManagementService(orderRepository);
    }

    @Test
    void createOrder() {
        //Given
        OrderEntity orderEntity = new OrderEntity();
        CreateOrderRequest orderRequest = new CreateOrderRequest();
        orderRequest.setOrderEntity(orderEntity);
        when(orderRepository.findOrderById(any())).thenReturn(orderEntity);

        //When
        orderManagementService.createOrder(orderRequest);

        //Then
        verify(orderRepository).saveOrder(orderEntity);
    }

    @Test
    void confirmOrder() {
        //Given
        OrderEntity orderEntity = new OrderEntity();
        when(orderRepository.findOrderById(any())).thenReturn(orderEntity);
        ConfimrOrderRequest confimrOrderRequest = new ConfimrOrderRequest();
        UUID randomUUID = UUID.randomUUID();
        confimrOrderRequest.setOrderId(randomUUID);

        //When
        orderManagementService.confirmOrder(randomUUID, confimrOrderRequest);

        //Then
        verify(orderRepository).findOrderById(randomUUID);
    }

    @Test
    void cancelOrder() {

        //Given
        OrderEntity orderEntity = new OrderEntity();
        when(orderRepository.findOrderById(any())).thenReturn(orderEntity);
        UUID randomUUID = UUID.randomUUID();

        //When
        orderManagementService.cancelOrder(randomUUID);

        //Then
        verify(orderRepository).findOrderById(randomUUID);
    }
}

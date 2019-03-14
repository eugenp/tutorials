package com.baeldung.hexagonal.store.application.controller;

import com.baeldung.hexagonal.store.application.base.Mapper;
import com.baeldung.hexagonal.store.application.dto.request.OrderCreateRequestDto;
import com.baeldung.hexagonal.store.application.dto.response.OrderResponseDto;
import com.baeldung.hexagonal.store.core.context.customer.exception.OrderNotFoundException;
import com.baeldung.hexagonal.store.core.context.order.entity.Order;
import com.baeldung.hexagonal.store.core.context.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {
    Mapper<Order, OrderResponseDto> orderResponseDtoMapper;
    OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService, Mapper<Order, OrderResponseDto> orderResponseDtoMapper) {
        this.orderService = orderService;
        this.orderResponseDtoMapper = orderResponseDtoMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable("id") int id) {
        Optional<Order> order = this.orderService.getOrderById(id);
        if (order.isPresent()) {
            return new ResponseEntity<>(this.orderResponseDtoMapper.map(order.get()), HttpStatus.OK);
        } else {
            throw new OrderNotFoundException();
        }
    }

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<OrderResponseDto> createOrder(@PathVariable("customerId") int customerId, @RequestBody OrderCreateRequestDto requestDto) {
        Optional<Order> order = this.orderService.processNewCustomerOrder(customerId, requestDto.getProductQuantityMap());
        return new ResponseEntity<>(this.orderResponseDtoMapper.map(order.get()), HttpStatus.OK);
    }

}

package com.baeldung.controller;

import static java.time.LocalDate.now;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.baeldung.model.Order;
import com.baeldung.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    CredentialService credentialService;

    public OrderController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @GetMapping
    public ResponseEntity<Collection<Order>> getAllOrders() {
        try {
            String apiKey = credentialService.getPassword("api_key");
            return new ResponseEntity<>(getOrderList(apiKey), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    private List<Order> getOrderList(String apiKey) throws Exception {
        if (!credentialMatch(apiKey))
            throw new Exception();
        Order order = new Order();
        order.setId(123L);
        order.setCustomerName("Craig");
        order.setOrderDate(now());
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        return orderList;
    }

    private boolean credentialMatch(String credValue) {
        //logic to check credValue
        return true;
    }
}


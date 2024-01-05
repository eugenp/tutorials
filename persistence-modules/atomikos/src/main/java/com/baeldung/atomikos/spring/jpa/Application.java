package com.baeldung.atomikos.spring.jpa;

import java.util.Set;
import java.util.UUID;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.atomikos.spring.jpa.inventory.Inventory;
import com.baeldung.atomikos.spring.jpa.inventory.InventoryRepository;
import com.baeldung.atomikos.spring.jpa.order.Order;
import com.baeldung.atomikos.spring.jpa.order.OrderRepository;

public class Application {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional(rollbackFor = Exception.class)
    public void placeOrder(String productId, int amount) throws Exception {

        String orderId = UUID.randomUUID()
            .toString();
        Inventory inventory = inventoryRepository.findOne(productId);
        inventory.setBalance(inventory.getBalance() - amount);
        inventoryRepository.save(inventory);
        Order order = new Order();
        order.setOrderId(orderId);
        order.setProductId(productId);
        order.setAmount(new Long(amount));
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        if (violations.size() > 0)
            throw new Exception("Invalid instance of an order.");
        orderRepository.save(order);

    }

}

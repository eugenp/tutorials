package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.domain.CustomerType;
import com.baeldung.hexagonal.domain.Order;
import com.baeldung.hexagonal.repository.OrderRepository;
import com.baeldung.hexagonal.repository.mongodb.MongoDbOrderRepository;
import com.baeldung.hexagonal.repository.mongodb.SpringDataMongoDbOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.UUID;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;

public class OrderServiceTest {

    @Mock
    private OrderRepository springDataOrderRepository;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        initMocks(this);
        orderService = new OrderService(springDataOrderRepository);
    }

    @Test
    void whenCustomerIsExempt_thenNoVatIsCharged() {
        Order o  = new Order();
        o.setAmount(100d);
        o.setCustomerType(CustomerType.EXEMPT);
        orderService.applyVAT(o);
        assertEquals(0d,o.getVat());
    }

    @Test
    void whenCustomerIsRegular_thenVatIsCharged() {
        Order o  = new Order();
        o.setAmount(100d);
        o.setCustomerType(CustomerType.REGULAR);
        orderService.applyVAT(o);
        assertNotEquals(0d,o.getVat());
    }

    @Test
    void whenOrderAlreadyHasId_thenNoIdIsCreated() {
        UUID id = UUID.randomUUID();
        Order o  = new Order();
        o.setId(id);
        o.setAmount(100d);
        o.setCustomerType(CustomerType.REGULAR);
        orderService.applyVAT(o);
        assertEquals(id,o.getId());
    }

    @Test
    void whenOrderHasNoId_thenAnIdIsCreated() {
        Order o  = new Order();
        o.setAmount(100d);
        o.setCustomerType(CustomerType.REGULAR);
        orderService.applyVAT(o);
        assertNotNull(o.getVat());
    }
}

package com.baeldung.domain;

import com.baeldung.domain.model.CoffeeOrder;
import com.baeldung.domain.ports.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CoffeeOrderServiceUnitTest {

    @Mock
    OrderRepository repository;

    CoffeeOrderService service;

    @BeforeEach
    void setUp() {
        service = new CoffeeOrderService(repository);
    }

    @Test
    void whenGivenOrder_thenAddToRepository() {
        service.addOrder("LATTE", "Address");
        Mockito.verify(repository).create(Mockito.any(CoffeeOrder.class));
    }
}

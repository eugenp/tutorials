package com.baeldung.dddhexagonalspring.infrastracture.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.baeldung.dddhexagonalspring.domain.Order;
import com.baeldung.dddhexagonalspring.domain.Product;
import com.baeldung.dddhexagonalspring.domain.repository.OrderRepository;
import com.baeldung.dddhexagonalspring.infrastracture.repository.cassandra.SpringDataCassandraOrderRepository;

@SpringJUnitConfig
@SpringBootTest
@TestPropertySource("classpath:ddd-layers-test.properties")
class CassandraDbOrderRepositoryIntegrationTest {

    @Autowired
    private SpringDataCassandraOrderRepository cassandraOrderRepository;

    @Autowired
    private OrderRepository orderRepository;

    @AfterEach
    void cleanUp() {
        cassandraOrderRepository.deleteAll();
    }

    @Test
    void shouldFindById_thenReturnOrder() {

        // given
        final UUID id = UUID.randomUUID();
        final Order order = createOrder(id);
        order.addOrder(new Product(UUID.randomUUID(), BigDecimal.TEN, "second"));
        order.complete();

        // when
        orderRepository.save(order);

        final Optional<Order> result = orderRepository.findById(id);

        assertEquals(order, result.get());
    }

    private Order createOrder(UUID id) {
        return new Order(id, new Product(UUID.randomUUID(), BigDecimal.TEN, "product"));
    }
}
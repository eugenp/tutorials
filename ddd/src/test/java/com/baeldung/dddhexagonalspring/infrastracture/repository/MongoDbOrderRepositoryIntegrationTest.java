package com.baeldung.dddhexagonalspring.infrastracture.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.baeldung.dddhexagonalspring.domain.Order;
import com.baeldung.dddhexagonalspring.domain.Product;
import com.baeldung.dddhexagonalspring.domain.repository.OrderRepository;

@SpringJUnitConfig
@SpringBootTest
@TestPropertySource("classpath:ddd-layers-test.properties")
class MongoDbOrderRepositoryIntegrationTest {

    @Autowired
    private SpringDataMongoOrderRepository mongoOrderRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @After
    void cleanUp(){
        mongoOrderRepository.deleteAll();
    }

    @Test
    void shouldFindById_thenReturnOrder() {
        
        // given
        final UUID id = UUID.randomUUID();
        final Order order = createOrder(id);
        
        // when
        orderRepository.save(order);

        final Optional<Order> result = orderRepository.findById(id);

        assertEquals(order, result.get());
    }

    private Order createOrder(UUID id) {
        return new Order(id, new Product(UUID.randomUUID(), BigDecimal.TEN, "product"));
    }
}
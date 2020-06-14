package com.baeldung.hexagonal.infrastructure.repository;

import com.baeldung.hexagonal.domain.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.data.Percentage.withPercentage;

@SpringBootTest
@Transactional
class OrderRepositoryImplIntegrationTest {
    @Autowired
    private OrderRepositoryImpl orderRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void givenExistingOrder_whenFindingOrder_thenReturnCorrectOrder() {
        Order initialOrder = new Order();
        initialOrder.setPrice(new BigDecimal(1000));
        orderRepository.save(initialOrder);

        this.entityManager.flush();
        this.entityManager.clear();

        Order retrievedOrder = this.orderRepository.findById(initialOrder.getId());
        assertThat(initialOrder.getPrice()).isCloseTo(retrievedOrder.getPrice(), withPercentage(0.0001));
    }

    @Test
    void whenOrderNotFound_shouldThrowException() {
        assertThatThrownBy(() -> {
            this.orderRepository.findById(12345L);
        }).isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("12345");

    }


}
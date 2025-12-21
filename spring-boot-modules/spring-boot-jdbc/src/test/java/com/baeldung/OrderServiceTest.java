package com.baeldung;


import com.baeldung.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    @Transactional
    void givenValidOrder_whenPlaced_thenTransactionCommits() {
        orderService.placeOrder(1L, 100L);
    }
}

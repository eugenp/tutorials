package com.baeldung.rwrouting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("rwrouting")
@SpringBootTest(classes = RwRoutingApplication.class)
class TransactionRoutingIntegrationTest {

    @Autowired
    OrderService orderService;

    @Test
    void whenSaveAndReadWithReadWrite_thenFindsOrder() {
        Order saved = orderService.save(new Order("laptop"));

        List<Order> result = orderService.findAllReadWrite();

        assertThat(result).anyMatch(o -> o.getId()
            .equals(saved.getId()));
    }

    @Test
    void whenSaveAndReadWithReadOnly_thenRoutesToReplica() {
        orderService.save(new Order("keyboard"));

        List<Order> result = orderService.findAllReadOnly();

        assertThat(result).isEmpty();
    }
}

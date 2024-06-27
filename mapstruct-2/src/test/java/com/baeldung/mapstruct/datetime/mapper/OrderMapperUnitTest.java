package com.baeldung.mapstruct.datetime.mapper;

import com.baeldung.mapstruct.datetime.model.LocalOrder;
import com.baeldung.mapstruct.datetime.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;

class OrderMapperUnitTest {

    private final OrderMapper mapper = OrderMapper.INSTANCE;

    @Test
    void whenLocalOrderIsMapped_thenGetsOrder() {
        LocalDateTime localDateTime = LocalDateTime.now();
        final long sourceEpochSecond = localDateTime.toEpochSecond(OrderMapper.DEFAULT_ZONE);
        LocalOrder localOrder = new LocalOrder();
        localOrder.setCreated(localDateTime);
        Order target = mapper.toOrder(localOrder);
        Assertions.assertNotNull(target);
        final long targetEpochSecond = target.getCreated().getEpochSecond();
        Assertions.assertEquals(sourceEpochSecond, targetEpochSecond);

    }

    @Test
    void whenOrderIsMapped_thenGetsLocalOrder() {
        Instant source = Instant.now();
        final long sourceEpochSecond = source.getEpochSecond();
        Order order = new Order();
        order.setCreated(source);
        LocalOrder target = mapper.toLocalOrder(order);
        Assertions.assertNotNull(target);
        final long targetEpochSecond = target.getCreated().toEpochSecond(OrderMapper.DEFAULT_ZONE);
        Assertions.assertEquals(sourceEpochSecond, targetEpochSecond);
    }
}

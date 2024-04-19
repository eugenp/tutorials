package com.baeldung.mapstruct.enumtostring.mapper;

import com.baeldung.mapstruct.enumtostring.model.ExternalOrder;
import com.baeldung.mapstruct.enumtostring.model.Order;
import com.baeldung.mapstruct.enumtostring.model.OrderType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.Assert.assertEquals;

class OrderMapperUnitTest {

    private final OrderMapper orderMapper = OrderMapper.INSTANCE;

    @ParameterizedTest
    @CsvSource({"1,Holiday preparations,SALE,Season Sale"})
    void whnOrderMappedToExternalOrder_thenExternalOrderGetsOrderDetails(Long orderId, String orderSummary, OrderType orderType, String externalOrderType) {
        Order order = new Order();
        order.setId(orderId);
        order.setSummary(orderSummary);
        order.setOrderType(orderType);

        final ExternalOrder externalOrder = orderMapper.toExternalOrder(order);
        assertEquals("ID does not match.", orderId, externalOrder.getId());
        assertEquals("Summary does not match.", orderSummary, externalOrder.getSummary());
        assertEquals("Order type does not match.", externalOrderType, externalOrder.getOrderType());
    }
}

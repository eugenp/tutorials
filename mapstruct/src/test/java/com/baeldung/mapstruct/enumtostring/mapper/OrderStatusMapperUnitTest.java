package com.baeldung.mapstruct.enumtostring.mapper;

import com.baeldung.mapstruct.enumtostring.model.OrderStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.Assert.assertEquals;

class OrderStatusMapperUnitTest {

    private final OrderStatusMapper mapper = OrderStatusMapper.INSTANCE;

    @ParameterizedTest
    @CsvSource({"CANCELLED,CANCELLED", "COMPLETED,COMPLETED", "FAILED,FAILED", "IN_PROGRESS,IN_PROGRESS", "RECEIVED,RECEIVED"})
    void testToString(OrderStatus source, String expected) {
        final String target = mapper.toString(source);
        assertEquals(expected, target);
    }

    @ParameterizedTest
    @CsvSource({"CANCELLED,CANCELLED", "COMPLETED,COMPLETED", "FAILED,FAILED", "IN_PROGRESS,IN_PROGRESS", "RECEIVED,RECEIVED"})
    void toOrderStatus(String source, OrderStatus expected) {
        final OrderStatus target = mapper.toOrderStatus(source);
        assertEquals(expected, target);
    }
}

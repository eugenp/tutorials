package com.baeldung.mapstruct.enumtostring.mapper;

import com.baeldung.mapstruct.enumtostring.model.OrderType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.Assert.assertEquals;

class OrderTypeMapperUnitTest {

    private final OrderTypeMapper mapper = OrderTypeMapper.INSTANCE;

    @ParameterizedTest
    @CsvSource({"BULK,BULK", "REGULAR,REGULAR", "SALE,SALE", "SUBSCRIPTION,SUBSCRIPTION"})
    void testToString(OrderType source, String expected) {
        final String target = mapper.toString(source);
        assertEquals(expected, target);
    }

    @ParameterizedTest
    @CsvSource({"BULK,BULK", "REGULAR,REGULAR", "SALE,SALE", "SUBSCRIPTION,SUBSCRIPTION"})
    void toOrderType(String source, OrderType expected) {
        final OrderType target = mapper.toOrderType(source);
        assertEquals(expected, target);
    }
}

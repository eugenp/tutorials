package com.baeldung.mapstruct.enumtostring.mapper;

import com.baeldung.mapstruct.enumtostring.model.OrderType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.Assert.assertEquals;

class OrderTypeMapperUnitTest {

    private final OrderTypeMapper mapper = OrderTypeMapper.INSTANCE;

    @ParameterizedTest
    @CsvSource({"BULK,BULK", "REGULAR,REGULAR", "SALE,SALE"})
    void whenOrderTypeIsMapped_thenGetsNameString(OrderType source, String expected) {
        final String target = mapper.toString(source);
        assertEquals(expected, target);
    }

    @ParameterizedTest
    @CsvSource({"BULK,Big Savings", "REGULAR,Daily Needs", "SALE,Season Sale"})
    void whenOrderTypeIsMapped_thenGetsDisplayString(OrderType source, String expected) {
        final String target = mapper.toDisplayString(source);
        assertEquals(expected, target);
    }

    @ParameterizedTest
    @CsvSource(
            {"BULK,BULK", "REGULAR,REGULAR", "SALE,SALE", "Big Savings,BULK", "Daily Needs,REGULAR", "Season Sale,SALE"})
    void whenStringIsMapped_thenGetsOrderType(String source, OrderType expected) {
        final OrderType target = mapper.toOrderType(source);
        assertEquals(expected, target);
    }
}

package com.baeldung.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.mapstruct.factory.Mappers;

import com.baeldung.dto.OrderDto;
import com.baeldung.entity.Order;
import com.baeldung.entity.Payment;

public class OrderMapperWithDefaultUnitTest {

    @Test
    public void whenOrderMapperWithDefaultToDtoMapsOrderWithEmptyProperties_thenDefaultValuesAreUsed() {
        OrderMapperWithDefault mapper = Mappers.getMapper(OrderMapperWithDefault.class);
        Order orderSource = new Order();
        orderSource.setPayment(new Payment("Cash", "82.8"));
        OrderDto mapped = mapper.toDto(orderSource);
        assertEquals("Cash", mapped.getPayment()
            .getType());
        assertEquals(82.8d, mapped.getPayment()
            .getAmount(), 0.01d);
        assertEquals("N/A", mapped.getTransactionId());
        assertNull(mapped.getOrderItemIds());
    }

    @Test
    public void whenOrderMapperWithDefaultToDtoMapsOrderWithNonEmptyProperties_thenSourceValuesAreUsed() {
        OrderMapperWithDefault mapper = Mappers.getMapper(OrderMapperWithDefault.class);
        Order orderSource = new Order();
        orderSource.setPayment(new Payment("Cash", "121.32"));
        orderSource.setOrderItemIds(List.of("item1", "item2"));
        orderSource.setTransactionId("orderTransaction");
        OrderDto mapped = mapper.toDto(orderSource);
        assertEquals("Cash", mapped.getPayment()
            .getType());
        assertEquals(121.32d, mapped.getPayment()
            .getAmount(), 0.001d);
        assertEquals("orderTransaction", mapped.getTransactionId());
        assertNotNull(mapped.getOrderItemIds());
        assertEquals(2, mapped.getOrderItemIds()
            .size());
    }

}

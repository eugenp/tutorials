package com.baeldung.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.mapstruct.factory.Mappers;

import com.baeldung.dto.OrderDto;
import com.baeldung.entity.Order;
import com.baeldung.entity.Payment;

public class OrderMapperWithAfterMappingUnitTest {

    @Test
    public void whenOrderMapperWithAfterMappingToDtoMapsOrderWithEmptyProperties_thenDefaultValuesAreUsed() {
        OrderMapperWithAfterMapping mapper = Mappers.getMapper(OrderMapperWithAfterMapping.class);
        Order orderSource = new Order();
        orderSource.setPayment(new Payment("Cash", "12.0"));
        OrderDto mapped = mapper.toDto(orderSource);
        assertEquals("Cash", mapped.getPayment()
            .getType());
        assertEquals(12.0d, mapped.getPayment()
            .getAmount(), 0.01d);
        assertEquals("N/A", mapped.getTransactionId());
        assertNotNull(mapped.getOrderItemIds());
        assertEquals(0, mapped.getOrderItemIds()
            .size());
    }

    @Test
    public void whenOrderMapperWithAfterMappingToDtoMapsOrderWithNonEmptyProperties_thenSourceValuesAreUsed() {
        OrderMapperWithAfterMapping mapper = Mappers.getMapper(OrderMapperWithAfterMapping.class);
        Order orderSource = new Order();
        orderSource.setPayment(new Payment("Cash", "13.1"));
        orderSource.setOrderItemIds(List.of("item1", "item2"));
        orderSource.setTransactionId("orderTransaction");
        OrderDto mapped = mapper.toDto(orderSource);
        assertEquals("Cash", mapped.getPayment()
            .getType());
        assertEquals(13.1d, mapped.getPayment()
            .getAmount(), 0.01d);
        assertEquals("orderTransaction", mapped.getTransactionId());
        assertNotNull(mapped.getOrderItemIds());
        assertEquals(2, mapped.getOrderItemIds()
            .size());
    }

}

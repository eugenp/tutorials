package com.baeldung.mapper;


import com.baeldung.dto.OrderDto;
import com.baeldung.entity.Order;
import com.baeldung.entity.Payment;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderMapperWithAfterMappingTest {

    @Test
    public void whenOrderMapperWithAfterMappingToDtoMapsOrderWithEmptyProperties_thenDefaultValuesAreUsed() {
        OrderMapperWithAfterMapping mapper = Mappers.getMapper(OrderMapperWithAfterMapping.class);
        Order orderSource = new Order();
        orderSource.setPayment(new Payment("Cash"));
        OrderDto mapped = mapper.toDto(orderSource);
        assertEquals("Cash", mapped.getPaymentDto().getType());
        assertEquals("N/A", mapped.getTransactionId());
        assertNotNull(mapped.getOrderItemIds());
        assertEquals(0, mapped.getOrderItemIds().size());
    }

    @Test
    public void whenOrderMapperWithAfterMappingToDtoMapsOrderWithNonEmptyProperties_thenSourceValuesAreUsed() {
        OrderMapperWithAfterMapping mapper = Mappers.getMapper(OrderMapperWithAfterMapping.class);
        Order orderSource = new Order();
        orderSource.setPayment(new Payment("Cash"));
        orderSource.setOrderItemIds(List.of("item1", "item2"));
        orderSource.setTransactionId("orderTransaction");
        OrderDto mapped = mapper.toDto(orderSource);
        assertEquals("Cash", mapped.getPaymentDto().getType());
        assertEquals("orderTransaction", mapped.getTransactionId());
        assertNotNull(mapped.getOrderItemIds());
        assertEquals(2, mapped.getOrderItemIds().size());
    }

}

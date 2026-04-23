package com.baeldung.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.baeldung.dto.PaymentDto;
import com.baeldung.entity.Payment;

public class AlwaysNullCheckPaymentMapperUnitTest {

    @Test
    public void whenPaymentIsNotNull_thenPaymentDtoIsCreated() {
        Payment source = new Payment("Cash", "12.2");
        AlwaysNullCheckPaymentMapper mapper = Mappers.getMapper(AlwaysNullCheckPaymentMapper.class);
        PaymentDto result = mapper.toDto(source);
        assertEquals("Cash", result.getType());
        assertEquals(12.2d, result.getAmount(), 0.01d);
    }

    @Test
    public void whenPaymentIsNull_thenPaymentDtoIsNull() {
        AlwaysNullCheckPaymentMapper mapper = Mappers.getMapper(AlwaysNullCheckPaymentMapper.class);
        PaymentDto result = mapper.toDto(null);
        assertNull(result);
    }

}

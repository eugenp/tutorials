package com.baeldung.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.baeldung.dto.OrderDto;
import com.baeldung.entity.Order;

@Mapper(uses = PaymentMapper.class)
public interface OrderMapperWithDefault {

    @Mapping(source = "payment", target = "payment", defaultExpression = "java(new com.baeldung.dto.PaymentDto())")
    @Mapping(source = "transactionId", target = "transactionId", defaultValue = "N/A")
    OrderDto toDto(Order order);

}

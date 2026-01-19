package com.baeldung.mapper;

import java.util.ArrayList;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.baeldung.dto.OrderDto;
import com.baeldung.entity.Order;

@Mapper(uses = PaymentMapper.class)
public interface OrderMapperWithAfterMapping {

    OrderDto toDto(Order order);

    @AfterMapping
    default OrderDto postProcessing(@MappingTarget OrderDto orderDto) {
        if (orderDto.getOrderItemIds() == null) {
            orderDto.setOrderItemIds(new ArrayList<>());
        }
        if (orderDto.getTransactionId() == null) {
            orderDto.setTransactionId("N/A");
        }
        return orderDto;
    }

}

package com.baeldung.hexagonal.store.application.dto.mapper;

import com.baeldung.hexagonal.store.application.base.Mapper;
import com.baeldung.hexagonal.store.application.dto.response.OrderProductResponseDto;
import com.baeldung.hexagonal.store.application.dto.response.OrderResponseDto;
import com.baeldung.hexagonal.store.core.context.order.entity.Order;
import com.baeldung.hexagonal.store.core.context.order.entity.OrderProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderEntityToDtoMapper implements Mapper<Order, OrderResponseDto> {

    @Autowired
    Mapper<OrderProduct, OrderProductResponseDto> orderProductResponseDtoMapper;

    @Override
    public OrderResponseDto map(Order source) {
        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setId(source.getId());
        responseDto.setStatus(source.getStatus());
        responseDto.setOrderProducts(orderProductResponseDtoMapper.mapList(source.getOrderProducts()));
        return responseDto;
    }

}

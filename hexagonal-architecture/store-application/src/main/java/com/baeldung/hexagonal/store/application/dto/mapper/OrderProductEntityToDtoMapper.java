package com.baeldung.hexagonal.store.application.dto.mapper;

import com.baeldung.hexagonal.store.application.base.Mapper;
import com.baeldung.hexagonal.store.application.dto.response.OrderProductResponseDto;
import com.baeldung.hexagonal.store.application.dto.response.ProductResponseDto;
import com.baeldung.hexagonal.store.core.context.order.entity.OrderProduct;
import com.baeldung.hexagonal.store.core.context.order.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderProductEntityToDtoMapper implements Mapper<OrderProduct, OrderProductResponseDto> {
    final Mapper<Product, ProductResponseDto> productResponseDtoMapper;

    @Autowired
    public OrderProductEntityToDtoMapper(Mapper<Product, ProductResponseDto> productResponseDtoMapper) {
        this.productResponseDtoMapper = productResponseDtoMapper;
    }

    @Override
    public OrderProductResponseDto map(OrderProduct source) {
        OrderProductResponseDto orderProductResponseDto = new OrderProductResponseDto();
        orderProductResponseDto.setProduct(this.productResponseDtoMapper.map(source.getProduct()));
        orderProductResponseDto.setQuantity(source.getQuantity());
        return orderProductResponseDto;
    }

}

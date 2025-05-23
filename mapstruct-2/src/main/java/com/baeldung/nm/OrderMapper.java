package com.baeldung.nm;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.baeldung.nm.entity.Order;
import com.baeldung.nm.entity.OrderDto;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "customer.name", target = "customerName")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "productPrice")
    @Mapping(source = "customer.address.city", target = "customerCity")
    @Mapping(source = "customer.address.zipCode", target = "customerZipCode")
    OrderDto orderToOrderDto(Order order);
}
package com.baeldung.nested;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.baeldung.nested.entity.Order;
import com.baeldung.nested.entity.OrderDto;

@Mapper
public abstract class AbstractOrderMapper {
    public static final AbstractOrderMapper INSTANCE = Mappers.getMapper(AbstractOrderMapper.class);

    public OrderDto orderToOrderDto(Order order) {
        OrderDto orderDto = applyCustomMappings(order);
        orderDto = mapCustomer(order);
        mapProduct(order, orderDto);
        return orderDto;
    }

    private OrderDto applyCustomMappings(Order order) {
        // Custom mapping logic can be applied here
        return new OrderDto();
    }

    @Mapping(source = "customer.name", target = "customerName")
    @Mapping(source = "customer.address.city", target = "customerCity")
    @Mapping(source = "customer.address.zipCode", target = "customerZipCode")
    protected abstract OrderDto mapCustomer(Order order);

    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "productPrice")
    protected abstract void mapProduct(Order order, @MappingTarget OrderDto orderDto);
}

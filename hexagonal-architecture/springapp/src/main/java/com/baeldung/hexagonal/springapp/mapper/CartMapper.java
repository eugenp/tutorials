package com.baeldung.hexagonal.springapp.mapper;

import static java.util.stream.Collectors.toList;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.baeldung.hexagonal.core.Cart;
import com.baeldung.hexagonal.springapp.entity.CartEntity;

@Mapper
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    CartEntity toEntity(Cart model);

    default Cart toModel(CartEntity entity) {
        return new Cart(
                CustomerMapper.INSTANCE.toModel(entity.getCustomer()),
                entity.getItems().stream().map(CartItemMapper.INSTANCE::toModel).collect(toList()),
                entity.getAppliedCoupons().stream().map(CouponMapper.INSTANCE::toModel).collect(toList()),
                entity.getEffectivePrice()
        );
    }
}

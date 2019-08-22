package com.baeldung.hexagonal.springapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.baeldung.hexagonal.core.CartItem;
import com.baeldung.hexagonal.springapp.entity.CartItemEntity;

@Mapper
public interface CartItemMapper {

    CartItemMapper INSTANCE = Mappers.getMapper(CartItemMapper.class);

    CartItemEntity toEntity(CartItem model);

    default CartItem toModel(CartItemEntity entity) {
       return new CartItem(ProductMapper.INSTANCE.toModel(entity.getProduct()), entity.getQuantity());
    }

}

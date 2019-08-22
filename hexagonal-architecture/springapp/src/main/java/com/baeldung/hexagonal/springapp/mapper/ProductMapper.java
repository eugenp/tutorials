package com.baeldung.hexagonal.springapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.baeldung.hexagonal.core.Product;
import com.baeldung.hexagonal.springapp.entity.ProductEntity;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductEntity toEntity(Product model);

    default Product toModel(ProductEntity entity) {
        return new Product(entity.getId(), entity.getName(), entity.getUnitPrice(), entity.getStockQuantity());
    }
}

package com.baeldung.hexagonal.store.application.dto.mapper;

import com.baeldung.hexagonal.store.application.base.Mapper;
import com.baeldung.hexagonal.store.application.dto.response.ProductResponseDto;
import com.baeldung.hexagonal.store.core.context.order.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityToDtoMapper implements Mapper<Product, ProductResponseDto> {

    @Override
    public ProductResponseDto map(Product source) {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setId(source.getId());
        responseDto.setName(source.getName());
        responseDto.setPrice(source.getPrice());
        return responseDto;
    }
}

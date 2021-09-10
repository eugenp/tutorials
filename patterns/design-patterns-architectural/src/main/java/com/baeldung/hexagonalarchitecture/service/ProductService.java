package com.baeldung.hexagonalarchitecture.service;

import com.baeldung.hexagonalarchitecture.dto.ProductDto;

import java.util.UUID;

public interface ProductService {
    ProductDto getProduct(UUID productId);
    void addProduct(ProductDto productDto);
}

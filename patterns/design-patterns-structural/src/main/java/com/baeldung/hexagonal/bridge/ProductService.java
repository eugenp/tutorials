package com.baeldung.hexagonal.bridge;

import com.baeldung.hexagonal.adapter.driven.ProductRepositoryAdapter;
import com.baeldung.hexagonal.domain.Product;
import com.baeldung.hexagonal.domain.ProductVO;

import java.util.List;
import java.util.stream.Collectors;

public class ProductService implements IBridge<ProductRequest, ProductResponse> {

    @Override
    public ProductResponse create(ProductRequest productRequest) {
        Product product = ProductRepositoryAdapter.getInstance()
                .create(productRequest.getProduct());
        return new ProductResponse(new ProductVO(product.getName()));
    }

    @Override
    public List<ProductResponse> list() {
        return ProductRepositoryAdapter.getInstance()
                .list()
                .stream()
                .map(p -> new ProductResponse(new ProductVO(p.getName())))
                .collect(Collectors.toList());
    }
}

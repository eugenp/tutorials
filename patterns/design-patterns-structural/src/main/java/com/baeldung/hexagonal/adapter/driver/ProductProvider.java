package com.baeldung.hexagonal.adapter.driver;

import com.baeldung.hexagonal.bridge.ProductRequest;
import com.baeldung.hexagonal.bridge.ProductResponse;
import com.baeldung.hexagonal.bridge.ProductService;
import com.baeldung.hexagonal.ports.driver.IProvide;

import java.util.List;

public class ProductProvider implements IProvide<ProductRequest, ProductResponse> {

    private ProductService service;

    public ProductProvider(ProductService service) {
        this.service = service;
    }

    @Override
    public List<ProductResponse> list() {
        return service.list();
    }
}

package com.baeldung.hexagonal.adapter.driver;

import com.baeldung.hexagonal.bridge.IBridge;
import com.baeldung.hexagonal.bridge.ProductRequest;
import com.baeldung.hexagonal.bridge.ProductResponse;
import com.baeldung.hexagonal.bridge.ProductService;
import com.baeldung.hexagonal.ports.driver.IModify;

public class ProductCreator implements IModify<ProductRequest, ProductResponse> {

    private ProductService service;

    public ProductCreator(ProductService service) {
        this.service = service;
    }

    @Override public ProductResponse execute(ProductRequest params) {
        ProductResponse response = service.create(params);
        return response;
    }

    //getters
    @Override public IBridge<ProductRequest, ProductResponse> getBridge() {
        return service;
    }

}

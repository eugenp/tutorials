package com.baeldung.hexagonal.adapter.driver;

import com.baeldung.hexagonal.bridge.ProductRequest;
import com.baeldung.hexagonal.bridge.ProductResponse;
import com.baeldung.hexagonal.bridge.ProductService;
import com.baeldung.hexagonal.ports.driven.INotify;
import com.baeldung.hexagonal.ports.driver.IModify;

public class ProductCreator implements IModify<ProductRequest, ProductResponse> {

    private ProductService productService;
    private INotify notifier;

    public ProductCreator(ProductService productService, INotify notifier) {
        this.productService = productService;
        this.notifier = notifier;
    }

    @Override
    public ProductResponse execute(ProductRequest params) {
        ProductResponse response = productService.create(params);
        notifier.sendNotification();
        return response;
    }

    //getters

    public ProductService getProductService() {
        return productService;
    }

    public INotify getNotifier() {
        return notifier;
    }
}

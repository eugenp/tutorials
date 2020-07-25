package com.architecture.hexagonal.component.impl;

import com.architecture.hexagonal.component.ProductRetriever;
import com.architecture.hexagonal.exception.ProductNotFoundException;
import com.architecture.hexagonal.model.Product;
import com.architecture.hexagonal.model.ProductStore;
import org.springframework.stereotype.Service;


@Service
public class ProductRetrieverImpl implements ProductRetriever {

    private ProductStore productStore;

    public ProductRetrieverImpl(ProductStore productStore) {
        this.productStore = productStore;
    }

    @Override
    public Product retrieveById(int productId) {
        Product product = this.productStore.findProductById(productId);
        if (product == null) {
            throw new ProductNotFoundException(String.format("Could not find product for productId %s", productId));
        }
        return product;
    }

}

package com.architecture.hexagonal.component.impl;

import com.architecture.hexagonal.component.ProductRetriever;
import com.architecture.hexagonal.component.ProductService;
import com.architecture.hexagonal.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRetriever productRetriever;

    public ProductServiceImpl(ProductRetriever productRetriever){
        this.productRetriever = productRetriever;
    }
    @Override
    public Product getProduct(int productId) {
        return productRetriever.retrieveById(productId);

    }
}

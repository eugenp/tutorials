package com.architecture.hexagonal.component.impl;

import com.architecture.hexagonal.component.ProductPathConfiguration;
import com.architecture.hexagonal.component.ProductRetriever;
import com.architecture.hexagonal.component.ResourceRetriever;
import com.architecture.hexagonal.exception.ProductNotFoundException;
import com.architecture.hexagonal.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProductRetrieverImpl implements ProductRetriever {

    private static final Logger LOG = LoggerFactory.getLogger(ProductRetrieverImpl.class);


    private ResourceRetriever resourceRetriever;
    private ProductPathConfiguration productPathConfiguration;
    private ObjectMapper mapper;

    public ProductRetrieverImpl(ResourceRetriever resourceRetriever,
                                ProductPathConfiguration productPathConfiguration, ObjectMapper mapper) {
        this.resourceRetriever = resourceRetriever;
        this.productPathConfiguration = productPathConfiguration;
        this.mapper = mapper;
    }

    @Override
    public Product retrieveById(int productId) {
        String productString = null;
        Product product = null;
        try {
            final String productResource = productPathConfiguration.getProductPath(productId);
            productString = resourceRetriever.retrieveResource(productResource);
            if (productString == null) {
                throw new ProductNotFoundException(String.format("Could not read product details for productId %s", productId));
            }
            product = mapper.readValue(productString, Product.class);
        } catch (IOException e) {
            throw new ProductNotFoundException(String.format("Could not read product details for productId %s", productId));
        }
        return product;
    }
}

package com.baeldung.opentelemetry.controller;

import com.baeldung.opentelemetry.api.client.PriceClient;
import com.baeldung.opentelemetry.model.Product;
import com.baeldung.opentelemetry.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final PriceClient priceClient;

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(PriceClient priceClient, ProductRepository productRepository) {
        this.priceClient = priceClient;
        this.productRepository = productRepository;
    }

    @GetMapping(path = "/product/{id}")
    public Product getProductDetails(@PathVariable("id") long productId){
        LOGGER.info("Getting Product and Price Details With Product Id {}", productId);
        Product product = productRepository.getProduct(productId);
        product.setPrice(priceClient.getPrice(productId));

        return product;
    }
}

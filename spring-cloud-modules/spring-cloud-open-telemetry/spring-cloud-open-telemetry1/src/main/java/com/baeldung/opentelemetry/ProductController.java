package com.baeldung.opentelemetry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private PriceClient priceClient;

    @Autowired
    private ProductRepository productRepository;


    @GetMapping(path = "/product/{id}")
    public Product getProductDetails(@PathVariable("id") long productId){
        logger.info("Getting Product and Price Details With Product Id {}", productId);
        Price price = priceClient.getPrice(productId);
        Product product = productRepository.getProduct(productId);
        product.setPrice(price);

        return product;
    }
}

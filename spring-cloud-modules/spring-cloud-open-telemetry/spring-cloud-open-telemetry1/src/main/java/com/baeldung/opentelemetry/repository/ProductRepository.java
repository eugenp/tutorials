package com.baeldung.opentelemetry.repository;

import com.baeldung.opentelemetry.exception.ProductNotFoundException;
import com.baeldung.opentelemetry.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProductRepository {

    private final Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    private final Map<Long, Product> productMap = new HashMap<>();

    public ProductRepository() {
        setupRepo();
    }

    public Product getProduct(Long productId){
        logger.info("Getting Product from Product Repo With Product Id {}", productId);

        if(!productMap.containsKey(productId)){
            logger.error("Product Not Found for Product Id {}", productId);
            throw new ProductNotFoundException("Product Not Found");
        }

        return productMap.get(productId);
    }

    private void setupRepo() {
        Product product1 = new Product();
        product1.setId(100001);
        product1.setName("apple");

        productMap.put(100001L, product1);

        Product product2 = new Product();
        product2.setId(100002);
        product2.setName("pears");

        productMap.put(100002L, product2);

        Product product3 = new Product();
        product3.setId(100003);
        product3.setName("banana");

        productMap.put(100003L, product3);

        Product product4 = new Product();
        product3.setId(100004);
        product3.setName("mango");

        productMap.put(100004L, product4);

        Product product5 = new Product();
        product3.setId(100005);
        product3.setName("test");

        productMap.put(100005L, product5);
    }
}

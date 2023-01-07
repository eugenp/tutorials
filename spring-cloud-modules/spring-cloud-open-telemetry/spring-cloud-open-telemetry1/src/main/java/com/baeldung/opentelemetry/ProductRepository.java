package com.baeldung.opentelemetry;

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
        product1.setDescription("apple desc");

        productMap.put(100001l, product1);

        Product product2 = new Product();
        product2.setId(100002);
        product2.setName("pears");
        product2.setDescription("pears desc");

        productMap.put(100002l, product2);

        Product product3 = new Product();
        product3.setId(100003);
        product3.setName("banana");
        product3.setDescription("banana desc");

        productMap.put(100003l, product3);

        Product product4 = new Product();
        product3.setId(100004);
        product3.setName("mango");
        product3.setDescription("mango desc");

        productMap.put(100004l, product4);

        Product product5 = new Product();
        product3.setId(100005);
        product3.setName("test");
        product3.setDescription("test desc");

        productMap.put(100005l, product5);
    }
}

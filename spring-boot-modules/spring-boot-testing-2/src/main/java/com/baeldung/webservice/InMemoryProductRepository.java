package com.baeldung.webservice;

import com.baeldung.webservice.generated.Product;
import org.springframework.stereotype.Component;

@Component
public class InMemoryProductRepository implements ProductRepository {

    public Product findProduct(String id) {
        Product product = new Product();
        product.setId(id);
        product.setName("Product " + id);
        return product;
    }
}

package com.baeldung.adapter;
import com.baeldung.model.Product;
import com.baeldung.port.ProductRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Service
public class ProductServiceAdapter implements ProductRepositoryPort {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional
    @Override
    public void create(String name, String description, long code) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setCode(code);
       
        entityManager.persist(product);
    }

    @Override
    public Product getProduct(Integer productId) {
        return entityManager.find(Product.class, productId);
    }
}
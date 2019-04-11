package com.baeldung.adapter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.baeldung.domain.Product;
import com.baeldung.port.ProductRepositoryPort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ProductServiceAdapter implements ProductRepositoryPort {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional
    @Override
    public void create(String productName, String desc, long price) {
        Product product = new Product();
        product.setProductName(productName);
        product.setDesc(desc);
        product.setPrice(price);
       
        entityManager.persist(product);
    }

    @Override
    public Product getProduct(Integer productId) {
        return entityManager.find(Product.class, productId);
    }
}
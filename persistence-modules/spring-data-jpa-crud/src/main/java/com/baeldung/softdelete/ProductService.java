package com.baeldung.softdelete;

import javax.persistence.EntityManager;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public void remove(Long id){
        productRepository.deleteById(id);
    }

    public Iterable<Product> findAll(boolean isDeleted){
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedProductFilter");
        filter.setParameter("isDeleted", isDeleted);
        Iterable<Product> products =  productRepository.findAll();
        session.disableFilter("deletedProductFilter");
        return products;
    }
}
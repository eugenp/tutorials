package com.baeldung.contextvsunit.service;

import org.springframework.stereotype.Service;

import com.baeldung.contextvsunit.entity.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import jakarta.transaction.Transactional;

@Service
public class PersistenceContextProductService {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManagerTransactionType;

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManagerExtendedType;

    @Transactional
    public void insertProductWithTransactionTypePersistenceContext(Product product) {
        entityManagerTransactionType.persist(product);
    }

    public Product findWithTransactionTypePersistenceContext(long id) {
        return entityManagerTransactionType.find(Product.class, id);
    }

    public void insertProductWithExtendedTypePersistenceContext(Product product) {
        entityManagerExtendedType.persist(product);
    }

    public Product findWithExtendedTypePersistenceContext(long id) {
        return entityManagerExtendedType.find(Product.class, id);
    }

}

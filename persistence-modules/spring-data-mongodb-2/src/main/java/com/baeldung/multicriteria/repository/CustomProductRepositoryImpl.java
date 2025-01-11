package com.baeldung.multicriteria.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.core.query.Query;

import com.baeldung.multicriteria.Product;
import com.baeldung.multicriteria.QProduct;
import com.querydsl.core.types.Predicate;
@Repository
public class CustomProductRepositoryImpl implements CustomProductRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Product> find(Query query, Class<Product> entityClass) {
        return mongoTemplate.find(query, entityClass);
    }
}


package com.baeldung.multicriteria.repository;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.baeldung.multicriteria.Product;
import com.querydsl.core.types.Predicate;

@Repository
public interface CustomProductRepository {
    List<Product> find(Query query, Class<Product> entityClass);
}

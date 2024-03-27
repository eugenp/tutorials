package com.baeldung.spring.data.jpa.queryjsonb;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
public class ProductSpecification implements Specification<Product> {

    private final String key;
    private final String value;

    public ProductSpecification(String key, String value) {
        this.key = key; this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.equal(cb.function("jsonb_extract_path_text", String.class, root.get("attributes"), cb.literal(key)), value);
    }
}
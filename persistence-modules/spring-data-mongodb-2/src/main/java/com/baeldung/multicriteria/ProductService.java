package com.baeldung.multicriteria;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.baeldung.multicriteria.repository.CustomProductRepository;
import com.baeldung.multicriteria.repository.ProductRepository;
import com.querydsl.core.types.Predicate;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomProductRepository customProductRepository;

    public List<Product> findProductsUsingAndOperator(String name, int minPrice, String category, boolean available) {
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(Criteria.where("name")
            .is(name), Criteria.where("price")
            .gt(minPrice), Criteria.where("category")
            .is(category), Criteria.where("available")
            .is(available)));
        return customProductRepository.find(query, Product.class);
    }

    public List<Product> findProductsUsingOrOperator(String category, int minPrice) {
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(Criteria.where("category")
            .is(category), Criteria.where("price")
            .gt(minPrice)));

        return customProductRepository.find(query, Product.class);
    }

    public List<Product> findProductsUsingAndOperatorAndOrOperator(String category1, int price1, String name1, boolean available1) {
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(new Criteria().andOperator(Criteria.where("category")
            .is(category1), Criteria.where("price")
            .gt(price1)), new Criteria().andOperator(Criteria.where("name")
            .is(name1), Criteria.where("available")
            .is(available1))));

        return customProductRepository.find(query, Product.class);
    }

    public List<Product> findProductsUsingChainMethod(String name1, int price1, String category1, boolean available1) {
        Criteria criteria = Criteria.where("name")
            .is(name1)
            .and("price")
            .gt(price1)
            .and("category")
            .is(category1)
            .and("available")
            .is(available1);
        return customProductRepository.find(new Query(criteria), Product.class);
    }

    public List<Product> findProductsUsingQueryDSLWithAndCondition(String category, boolean available, String name, double minPrice) {
        QProduct qProduct = QProduct.product;
        Predicate predicate = qProduct.category.eq(category)
            .and(qProduct.available.eq(available))
            .and(qProduct.name.eq(name))
            .and(qProduct.price.gt(minPrice));

        return StreamSupport.stream(productRepository.findAll(predicate)
                .spliterator(), false)
            .collect(Collectors.toList());
    }

    List<Product> findProductsUsingQueryDSLWithOrCondition(String category, String name, double minPrice) {
        QProduct qProduct = QProduct.product;
        Predicate predicate = qProduct.category.eq(category)
            .or(qProduct.name.eq(name))
            .or(qProduct.price.gt(minPrice));
        return StreamSupport.stream(productRepository.findAll(predicate)
                .spliterator(), false)
            .collect(Collectors.toList());
    }

    List<Product> findProductsUsingQueryDSLWithAndOrCondition(String category, boolean available, String name, double minPrice) {
        QProduct qProduct = QProduct.product;
        Predicate predicate = qProduct.category.eq(category)
            .and(qProduct.available.eq(available))
            .or(qProduct.name.eq(name)
                .and(qProduct.price.gt(minPrice)));
        return StreamSupport.stream(productRepository.findAll(predicate)
                .spliterator(), false)
            .collect(Collectors.toList());
    }

    public List<Product> findProductsUsingQueryAnnotationWithAndCondition(String name1, int price1, String category1, boolean available) {
        return productRepository.findProductsByNamePriceCategoryAndAvailability(name1, price1, category1, available);
    }

    public List<Product> findProductsUsingQueryAnnotationWithOrCondition(String category1, boolean available1, int price1) {
        return productRepository.findProductsByCategoryAndAvailabilityOrPrice(category1, available1, price1);
    }
}
package com.baeldung.jpa.projections;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class ProductRepository {
    private EntityManager entityManager;

    public ProductRepository() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa-projections");
        entityManager = factory.createEntityManager();
    }
    
    @SuppressWarnings("unchecked")
    public List<Object> findAllNamesUsingJPQL() {
        Query query = entityManager.createQuery("select name from Product");
        List<Object> resultList = query.getResultList();
        return resultList;
    }
    
    @SuppressWarnings("unchecked")
    public List<Object> findAllIdsUsingJPQL() {
        Query query = entityManager.createQuery("select id from Product");
        List<Object> resultList = query.getResultList();
        return resultList;
    }
    
    public List<String> findAllNamesUsingCriteriaBuilder() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> query = builder.createQuery(String.class);
        Root<Product> product = query.from(Product.class);
        query.select(product.get("name"));
        List<String> resultList = entityManager.createQuery(query).getResultList();
        return resultList;
    }
    
    @SuppressWarnings("unchecked")
    public List<Object[]> findAllIdAndNamesUsingJPQL() {
        Query query = entityManager.createQuery("select id, name from Product");
        List<Object[]> resultList = query.getResultList();
        return resultList;
    }
    
    public List<Object[]> findAllIdAndNamesUsingCriteriaBuilderArray() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root<Product> product = query.from(Product.class);
        query.select(builder.array(product.get("id"), product.get("name")));
        List<Object[]> resultList = entityManager.createQuery(query).getResultList();
        return resultList;
    }
    
    public List<Object[]> findAllIdNameUnitPriceUsingCriteriaQueryMultiselect() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root<Product> product = query.from(Product.class);
        query.multiselect(product.get("id"), product.get("name"), product.get("unitPrice"));
        List<Object[]> resultList = entityManager.createQuery(query).getResultList();
        return resultList;
    }
    
    public List<Tuple> findAllIdAndNamesUsingCriteriaBuilderTuple() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createQuery(Tuple.class);
        Root<Product> product = query.from(Product.class);
        query.select(builder.tuple(product.get("id"), product.get("name")));
        List<Tuple> resultList = entityManager.createQuery(query).getResultList();
        return resultList;
    }
    
    public List<Object[]> findCountByCategoryUsingJPQL() {
        Query query = entityManager.createQuery("select p.category, count(p) from Product p group by p.category");
        return query.getResultList();
    }
    
    public List<Object[]> findCountByCategoryUsingCriteriaBuilder() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root<Product> product = query.from(Product.class);
        query.multiselect(product.get("category"), builder.count(product));
        query.groupBy(product.get("category"));
        List<Object[]> resultList = entityManager.createQuery(query).getResultList();
        return resultList;
    }
}

package com.baeldung.jpa.criteria.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.baeldung.jpa.criteria.entity.Item;
import com.baeldung.jpa.criteria.repository.CustomItemRepository;

public class CustomItemRepositoryImpl implements CustomItemRepository {

    private EntityManager entityManager;

    public CustomItemRepositoryImpl() {
        super();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa-h2-criteria");
        entityManager = factory.createEntityManager();
    }

    @Override
    public List<Item> findItemsByColorAndGrade() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaQuery = criteriaBuilder.createQuery(Item.class);
        Root<Item> itemRoot = criteriaQuery.from(Item.class);

        Predicate predicateForBlueColor = criteriaBuilder.equal(itemRoot.get("color"), "blue");
        Predicate predicateForRedColor = criteriaBuilder.equal(itemRoot.get("color"), "red");
        Predicate predicateForColor = criteriaBuilder.or(predicateForBlueColor, predicateForRedColor);

        Predicate predicateForGradeA = criteriaBuilder.equal(itemRoot.get("grade"), "A");
        Predicate predicateForGradeB = criteriaBuilder.equal(itemRoot.get("grade"), "B");
        Predicate predicateForGrade = criteriaBuilder.or(predicateForGradeA, predicateForGradeB);

        // final search filter
        Predicate finalPredicate = criteriaBuilder.and(predicateForColor, predicateForGrade);

        criteriaQuery.where(finalPredicate);

        List<Item> items = entityManager.createQuery(criteriaQuery)
            .getResultList();
        return items;
    }

    @Override
    public List<Item> findItemByColorOrGrade() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaQuery = criteriaBuilder.createQuery(Item.class);
        Root<Item> itemRoot = criteriaQuery.from(Item.class);

        Predicate predicateForBlueColor = criteriaBuilder.equal(itemRoot.get("color"), "red");
        Predicate predicateForGradeA = criteriaBuilder.equal(itemRoot.get("grade"), "D");
        Predicate predicateForBlueColorAndGradeA = criteriaBuilder.and(predicateForBlueColor, predicateForGradeA);

        Predicate predicateForRedColor = criteriaBuilder.equal(itemRoot.get("color"), "blue");
        Predicate predicateForGradeB = criteriaBuilder.equal(itemRoot.get("grade"), "B");
        Predicate predicateForRedColorAndGradeB = criteriaBuilder.and(predicateForRedColor, predicateForGradeB);

        // final search filter
        Predicate finalPredicate = criteriaBuilder.or(predicateForBlueColorAndGradeA, predicateForRedColorAndGradeB);

        criteriaQuery.where(finalPredicate);

        List<Item> items = entityManager.createQuery(criteriaQuery)
            .getResultList();
        return items;
    }
}

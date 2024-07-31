package com.baeldung.jpa.querytypes;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

/**
 * JPA Query Types examples. All using the UserEntity class.
 *
 * @author Rodolfo Felipe
 */
public class QueryTypesExamples {

    EntityManagerFactory emf;

    public QueryTypesExamples() {
        Map properties = new HashMap();
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        emf = Persistence.createEntityManagerFactory("jpa-query-types", properties);
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public UserEntity getUserByIdWithPlainQuery(Long id) {
        Query jpqlQuery = getEntityManager().createQuery("SELECT u FROM UserEntity u WHERE u.id=:id");
        jpqlQuery.setParameter("id", id);
        return (UserEntity) jpqlQuery.getSingleResult();
    }

    public UserEntity getUserByIdWithTypedQuery(Long id) {
        TypedQuery<UserEntity> typedQuery = getEntityManager().createQuery("SELECT u FROM UserEntity u WHERE u.id=:id", UserEntity.class);
        typedQuery.setParameter("id", id);
        return typedQuery.getSingleResult();
    }

    public UserEntity getUserByIdWithNamedQuery(Long id) {
        Query namedQuery = getEntityManager().createNamedQuery("UserEntity.findByUserId");
        namedQuery.setParameter("userId", id);
        return (UserEntity) namedQuery.getSingleResult();
    }

    public UserEntity getUserByIdWithNativeQuery(Long id) {
        Query nativeQuery = getEntityManager().createNativeQuery("SELECT * FROM users WHERE id=:userId", UserEntity.class);
        nativeQuery.setParameter("userId", id);
        return (UserEntity) nativeQuery.getSingleResult();
    }

    public UserEntity getUserByIdWithCriteriaQuery(Long id) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
        Root<UserEntity> userRoot = criteriaQuery.from(UserEntity.class);
        UserEntity queryResult = getEntityManager().createQuery(criteriaQuery.select(userRoot)
            .where(criteriaBuilder.equal(userRoot.get("id"), id)))
            .getSingleResult();
        return queryResult;
    }

}

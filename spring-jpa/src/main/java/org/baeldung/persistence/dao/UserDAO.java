package org.baeldung.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.baeldung.persistence.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO implements IUserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> searchUser(final String first, final String last, final int minAge) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<User> query = builder.createQuery(User.class);
        final Root r = query.from(User.class);

        Predicate predicate = builder.conjunction();
        predicate = builder.and(predicate, builder.like(r.get("firstName"), "%" + first + "%"));
        predicate = builder.and(predicate, builder.like(r.get("lastName"), "%" + last + "%"));
        predicate = builder.and(predicate, builder.greaterThanOrEqualTo(r.get("age"), minAge));

        query.where(predicate);

        final List<User> result = entityManager.createQuery(query).getResultList();
        return result;
    }

    @Override
    public void save(final User entity) {
        entityManager.persist(entity);
    }

}

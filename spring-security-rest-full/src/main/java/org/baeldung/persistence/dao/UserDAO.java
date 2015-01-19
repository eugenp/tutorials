package org.baeldung.persistence.dao;

import java.util.List;
import java.util.Map;

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
    public List<User> searchUser(final Map<String, Object> params) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<User> query = builder.createQuery(User.class);
        final Root r = query.from(User.class);

        Predicate predicate = builder.conjunction();

        for (final Map.Entry<String, Object> param: params.entrySet()){
            if (param.getKey().equalsIgnoreCase("age")) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(r.get(param.getKey()), Integer.parseInt(param.getValue().toString())));
            }
            else{
                predicate = builder.and(predicate, builder.like(r.get(param.getKey()), "%" + param.getValue() + "%"));
            }
        }
        query.where(predicate);

        final List<User> result = entityManager.createQuery(query).getResultList();
        return result;
    }

    @Override
    public void save(final User entity) {
        entityManager.persist(entity);
    }

}

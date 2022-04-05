package com.baeldung.spring.data.jpa.query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findUserByEmails(Set<String> emails) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> user = query.from(User.class);

        Path<String> emailPath = user.get("email");

        List<Predicate> predicates = new ArrayList<>();
        for (String email : emails) {

            predicates.add(cb.like(emailPath, email));

        }
        query.select(user)
            .where(cb.or(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query)
            .getResultList();
    }
    
    @Override
    public List<User> findAllUsersByPredicates(Collection<java.util.function.Predicate<User>> predicates) {
        List<User> allUsers = entityManager.createQuery("select u from User u", User.class).getResultList();
        Stream<User> allUsersStream = allUsers.stream();
        for (java.util.function.Predicate<User> predicate : predicates) {
            allUsersStream = allUsersStream.filter(predicate);
        }

        return allUsersStream.collect(Collectors.toList());
    }
}

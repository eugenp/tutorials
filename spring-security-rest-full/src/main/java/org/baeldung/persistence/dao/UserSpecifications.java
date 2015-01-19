package org.baeldung.persistence.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.baeldung.persistence.model.User;
import org.baeldung.persistence.model.User_;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<User> firstNameIsLike(final String term) {

        return new Specification<User>() {
            @Override
            public Predicate toPredicate(final Root<User> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
                return cb.like(root.get(User_.firstName), "%"+term+"%");
            }

        };
    }

    public static Specification<User> lastNameIsLike(final String term) {

        return new Specification<User>() {
            @Override
            public Predicate toPredicate(final Root<User> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
                return cb.like(root.get(User_.lastName), "%" + term + "%");
            }

        };
    }

    public static Specification<User> ageIsGreaterThan(final int minAge) {

        return new Specification<User>() {
            @Override
            public Predicate toPredicate(final Root<User> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
                return cb.greaterThanOrEqualTo(root.get(User_.age), minAge);
            }

        };
    }
}

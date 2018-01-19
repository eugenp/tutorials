package org.baeldung.persistence.dao;

import org.baeldung.persistence.model.User;
import org.baeldung.persistence.model.User_;
import org.baeldung.web.util.SpecSearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Optional;

public class UserSpecification implements Specification<User> {

    private SpecSearchCriteria criteria;
    private boolean joinAddress;

    public UserSpecification(final SpecSearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    public UserSpecification(final SpecSearchCriteria criteria, boolean joinAddress) {
        super();
        this.criteria = criteria;
        this.joinAddress = joinAddress;
    }

    public SpecSearchCriteria getCriteria() {
        return criteria;
    }

    @Override
    public Predicate toPredicate(final Root<User> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        if (joinAddress) {
            fetch(root, User_.address.getName());
        }
        switch (criteria.getOperation()) {
            case EQUALITY:
                return builder.equal(expression(root, criteria.key()), criteria.getValue());
            case NEGATION:
                return builder.notEqual(expression(root, criteria.key()), criteria.getValue());
            case GREATER_THAN:
                return builder.greaterThan(expression(root, criteria.key()), criteria.getValue());
            case LESS_THAN:
                return builder.lessThan(expression(root, criteria.key()), criteria.getValue());
            case LIKE:
                return builder.like(stringExpression(root, criteria.key()), criteria.getValue().toString());
            case STARTS_WITH:
                return builder.like(stringExpression(root, criteria.key()), criteria.getValue() + "%");
            case ENDS_WITH:
                return builder.like(stringExpression(root, criteria.key()), "%" + criteria.getValue());
            case CONTAINS:
                return builder.like(stringExpression(root, criteria.key()), "%" + criteria.getValue() + "%");
            default:
                return null;
        }
    }

    private Path<? extends Comparable> expression(Root<User> root, String key) {
        if (key.contains(".")) {
            String[] fields = key.split("\\.");
            fetch(root, fields[0]);
            return root.get(fields[0]).get(fields[1]);
        }
        return root.get(key);
    }

    private Path<String> stringExpression(Root<User> root, String key) {
        if (key.contains(".")) {
            String[] fields = key.split("\\.");
            fetch(root, fields[0]);
            return root.get(fields[0]).get(fields[1]);
        }
        return root.get(key);
    }

    private void fetch(Root<User> root, String field) {
        Optional<Fetch<User, ?>> isJoin = root.getFetches().stream()
                .filter(f -> f.getAttribute().getName().equals(field))
                .findFirst();
        if (!isJoin.isPresent()) {
            root.fetch(field, JoinType.LEFT);
        }
    }

}

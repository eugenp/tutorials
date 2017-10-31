package org.baeldung.persistence.dao;

import org.baeldung.persistence.model.User;
import org.baeldung.web.util.SpecSearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Optional;

public class UserSpecification implements Specification<User> {

	private SpecSearchCriteria criteria;

	public UserSpecification(final SpecSearchCriteria criteria) {
		super();
		this.criteria = criteria;
	}

	public SpecSearchCriteria getCriteria() {
		return criteria;
	}

	@Override
	public Predicate toPredicate(final Root<User> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
		switch (criteria.getOperation()) {
		case EQUALITY:
			return builder.equal(expression(root, criteria.key()), criteria.getValue());
		case NEGATION:
			return builder.notEqual(expression(root, criteria.key()), criteria.getValue());
		case GREATER_THAN:
			return builder.greaterThan(expression(root, criteria.key()), criteria.getValue().toString());
		case LESS_THAN:
			return builder.lessThan(expression(root, criteria.key()), criteria.getValue().toString());
		case LIKE:
			return builder.like(expression(root, criteria.key()), criteria.getValue().toString());
		case STARTS_WITH:
			return builder.like(expression(root, criteria.key()), criteria.getValue() + "%");
		case ENDS_WITH:
			return builder.like(expression(root, criteria.key()), "%" + criteria.getValue());
		case CONTAINS:
			return builder.like(expression(root, criteria.key()), "%" + criteria.getValue() + "%");
		default:
			return null;
		}
	}

	private Path<String> expression(Root<User> root, String key) {
		if (key.contains(".")) {
			String[] fields = key.split("\\.");
			Optional<Fetch<User, ?>> isJoin = root.getFetches().stream()
					.filter(f -> f.getAttribute().getName().equals(fields[0]))
					.findFirst();
			if (!isJoin.isPresent()) {
				root.fetch(fields[0], JoinType.LEFT);
			}
			return root.get(fields[0]).get(fields[1]);
		}
		return root.get(key);
	}

}

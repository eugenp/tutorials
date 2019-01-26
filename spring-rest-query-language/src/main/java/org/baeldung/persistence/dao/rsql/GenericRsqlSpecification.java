package org.baeldung.persistence.dao.rsql;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class GenericRsqlSpecification<T> implements Specification<T> {

    private String property;
    private ComparisonOperator operator;
    private List<String> arguments;

    public GenericRsqlSpecification(final String property, final ComparisonOperator operator, final List<String> arguments) {
        super();
        this.property = property;
        this.operator = operator;
        this.arguments = arguments;
    }

    @Override
    public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        final List<Object> args = castArguments(root);
        final Object argument = args.get(0);
        switch (RsqlSearchOperation.getSimpleOperator(operator)) {

        case EQUAL: {
            if (argument instanceof String) {
                return builder.like(root.get(property), argument.toString().replace('*', '%'));
            } else if (argument == null) {
                return builder.isNull(root.get(property));
            } else {
                return builder.equal(root.get(property), argument);
            }
        }
        case NOT_EQUAL: {
            if (argument instanceof String) {
                return builder.notLike(root.<String> get(property), argument.toString().replace('*', '%'));
            } else if (argument == null) {
                return builder.isNotNull(root.get(property));
            } else {
                return builder.notEqual(root.get(property), argument);
            }
        }
        case GREATER_THAN: {
            return builder.greaterThan(root.<String> get(property), argument.toString());
        }
        case GREATER_THAN_OR_EQUAL: {
            return builder.greaterThanOrEqualTo(root.<String> get(property), argument.toString());
        }
        case LESS_THAN: {
            return builder.lessThan(root.<String> get(property), argument.toString());
        }
        case LESS_THAN_OR_EQUAL: {
            return builder.lessThanOrEqualTo(root.<String> get(property), argument.toString());
        }
        case IN:
            return root.get(property).in(args);
        case NOT_IN:
            return builder.not(root.get(property).in(args));
        }

        return null;
    }

    // === private

    private List<Object> castArguments(final Root<T> root) {
        final List<Object> args = new ArrayList<Object>();
        final Class<? extends Object> type = root.get(property).getJavaType();

        for (final String argument : arguments) {
            if (type.equals(Integer.class)) {
                args.add(Integer.parseInt(argument));
            } else if (type.equals(Long.class)) {
                args.add(Long.parseLong(argument));
            } else {
                args.add(argument);
            }
        }

        return args;
    }

}

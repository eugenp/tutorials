package org.baeldung.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.baeldung.web.util.SearchCriteria;

import com.mysema.query.types.expr.BooleanExpression;

public class MyUserPredicatesBuilder {
    public static BooleanExpression buildUserPredicates(final List<SearchCriteria> params) {
        if (params.size() == 0)
            return null;

        final List<BooleanExpression> predicates = new ArrayList<BooleanExpression>();
        MyUserPredicate predicate;

        for (final SearchCriteria param : params) {
            predicate = new MyUserPredicate(param);
            final BooleanExpression exp = predicate.getPredicate();
            if (exp != null) {
                predicates.add(exp);
            }
        }

        if (predicates.size() == 0)
            return null;

        BooleanExpression result = predicates.get(0);
        for (int i = 1; i < predicates.size(); i++) {
            result = result.and(predicates.get(i));
        }
        return result;

    }
}

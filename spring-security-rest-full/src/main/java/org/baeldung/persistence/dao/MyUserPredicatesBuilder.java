package org.baeldung.persistence.dao;

import static org.baeldung.persistence.dao.MyUserPrdicates.ageIsGreaterThan;
import static org.baeldung.persistence.dao.MyUserPrdicates.firstNameIsLike;
import static org.baeldung.persistence.dao.MyUserPrdicates.lastNameIsLike;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mysema.query.types.expr.BooleanExpression;

public class MyUserPredicatesBuilder {
    public static BooleanExpression buildUserPredicates(final Map<String, Object> params) {
        if (params.size() == 0)
            return null;

        final List<BooleanExpression> predicates = new ArrayList<BooleanExpression>();
        String key, value;

        for (final Map.Entry<String, Object> param : params.entrySet()) {
            key = param.getKey();
            value = param.getValue().toString();
            if (key.equalsIgnoreCase("age")) {
                predicates.add(ageIsGreaterThan(Integer.parseInt(value)));
            } else if (key.equalsIgnoreCase("firstName")) {
                predicates.add(firstNameIsLike(value));
            } else if (key.equalsIgnoreCase("lastName")) {
                predicates.add(lastNameIsLike(value));
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

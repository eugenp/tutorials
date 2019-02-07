package org.baeldung.persistence.dao;

import org.baeldung.persistence.model.MyUser;
import org.baeldung.web.util.SearchCriteria;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.PathBuilder;
import com.mysema.query.types.path.StringPath;

public class MyUserPredicate {

    private SearchCriteria criteria;

    public MyUserPredicate() {

    }

    public MyUserPredicate(final SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public BooleanExpression getPredicate() {
        final PathBuilder<MyUser> entityPath = new PathBuilder<MyUser>(MyUser.class, "myUser");

        if (isNumeric(criteria.getValue().toString())) {
            System.out.println("Nuumber");
            final NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
            final int value = Integer.parseInt(criteria.getValue().toString());
            if (criteria.getOperation().equalsIgnoreCase(":")) {
                return path.eq(value);
            } else if (criteria.getOperation().equalsIgnoreCase(">")) {
                return path.goe(value);
            } else if (criteria.getOperation().equalsIgnoreCase("<")) {
                return path.loe(value);
            }
        } else {
            final StringPath path = entityPath.getString(criteria.getKey());
            if (criteria.getOperation().equalsIgnoreCase(":")) {
                return path.containsIgnoreCase(criteria.getValue().toString());
            }
        }
        return null;
    }

    public SearchCriteria getCriteria() {
        return criteria;
    }

    public void setCriteria(final SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public static boolean isNumeric(final String str) {
        try {
            Integer.parseInt(str);
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }
}

package org.baeldung.persistence.dao;

import org.baeldung.persistence.model.QMyUser;

import com.mysema.query.types.expr.BooleanExpression;

public class MyUserPrdicates {

    public static BooleanExpression firstNameIsLike(final String term) {
        final QMyUser user = QMyUser.myUser;
        return user.firstName.containsIgnoreCase(term);
    }

    public static BooleanExpression lastNameIsLike(final String term) {
        final QMyUser user = QMyUser.myUser;
        return user.lastName.containsIgnoreCase(term);
    }

    public static BooleanExpression ageIsGreaterThan(final int minAge) {
        final QMyUser user = QMyUser.myUser;
        return user.age.goe(minAge);
    }
}

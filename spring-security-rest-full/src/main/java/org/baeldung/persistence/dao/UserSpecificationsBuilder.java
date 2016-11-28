package org.baeldung.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.baeldung.persistence.model.User;
import org.baeldung.web.util.SearchOperation;
import org.baeldung.web.util.SpecSearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

public final class UserSpecificationsBuilder {

    private final List<SpecSearchCriteria> params;

    public UserSpecificationsBuilder() {
        params = new ArrayList<SpecSearchCriteria>();
    }

    // API

    public final UserSpecificationsBuilder with(final String key, final String operation, final Object value, final String prefix, final String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) // the operation may be complex operation
            {
                final boolean startWithAsterisk = prefix.contains("*");
                final boolean endWithAsterisk = suffix.contains("*");

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            params.add(new SpecSearchCriteria(key, op, value));
        }
        return this;
    }

    public Specification<User> build() {
        if (params.size() == 0) {
            return null;
        }

        final List<Specification<User>> specs = new ArrayList<Specification<User>>();
        for (final SpecSearchCriteria param : params) {
            specs.add(new UserSpecification(param));
        }

        Specification<User> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specifications.where(result).and(specs.get(i));
        }
        return result;
    }

}

package org.baeldung.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.baeldung.persistence.model.User;
import org.baeldung.web.util.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

public class UserSpecificationsBuilder {

    public static Specification<User> buildUserSpecs(final List<SearchCriteria> params) {
        if (params.size() == 0)
            return null;

        final List<Specification<User>> specs = new ArrayList<Specification<User>>();

        for (final SearchCriteria param : params) {
            specs.add(new UserSpecification(param));
        }

        if (specs.size() == 0)
            return null;

        Specification<User> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specifications.where(result).and(specs.get(i));
        }
        return result;

    }
}

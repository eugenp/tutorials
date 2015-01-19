package org.baeldung.persistence.dao;

import static org.baeldung.persistence.dao.UserSpecifications.ageIsGreaterThan;
import static org.baeldung.persistence.dao.UserSpecifications.firstNameIsLike;
import static org.baeldung.persistence.dao.UserSpecifications.lastNameIsLike;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.baeldung.persistence.model.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

public class UserSpecificationsBuilder {

    public static Specification<User> buildUserSpecs(final Map<String, Object> params) {
        if (params.size() == 0)
            return null;

        final List<Specification<User>> specs = new ArrayList<Specification<User>>();
        String key, value;

        for (final Map.Entry<String, Object> param : params.entrySet()) {
            key = param.getKey();
            value = param.getValue().toString();
            if (key.equalsIgnoreCase("age")) {
                specs.add(ageIsGreaterThan(Integer.parseInt(value)));
            } else if (key.equalsIgnoreCase("firstName")) {
                specs.add(firstNameIsLike(value));
            } else if (key.equalsIgnoreCase("lastName")) {
                specs.add(lastNameIsLike(value));
            }
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

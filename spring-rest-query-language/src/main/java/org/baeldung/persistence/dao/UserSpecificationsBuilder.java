package org.baeldung.persistence.dao;

import org.baeldung.persistence.model.Address_;
import org.baeldung.persistence.model.User;
import org.baeldung.persistence.model.User_;
import org.baeldung.web.util.SearchOperation;
import org.baeldung.web.util.SpecSearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public final class UserSpecificationsBuilder {

    private final List<SpecSearchCriteria> params;

    public UserSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    // API

    public final UserSpecificationsBuilder with(final String key, final String operation, final Object value, final String prefix, final String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public final UserSpecificationsBuilder with(final String orPredicate, final String key, final String operation, final Object value, final String prefix, final String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) { // the operation may be complex operation
                final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            params.add(new SpecSearchCriteria(orPredicate, key, op, value));
        }
        return this;
    }

    public Specification<User> build() {

        if (params.size() == 0)
            return null;

        Map<String, String> userAtts = entityAttributes(User_.class);
        Map<String, String> addressAtts = entityAttributes(Address_.class);
        Specification<User> result = new UserSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i)
                .isOrPredicate()
                    ? Specifications.where(result)
                        .or(new UserSpecification(params.get(i)))
                    : Specifications.where(result)
                        .and(new UserSpecification(params.get(i)));

        }

        return result;
    }

    public final UserSpecificationsBuilder with(UserSpecification spec) {
        params.add(spec.getCriteria());
        return this;
    }

    public final UserSpecificationsBuilder with(SpecSearchCriteria criteria) {
        params.add(criteria);
        return this;
    }

    private Map<String, String> entityAttributes(Class class_) {
        return Arrays.stream(class_.getDeclaredFields())
                .filter(t -> t.getGenericType() instanceof ParameterizedType)
                .collect(toMap(f -> f.getName(), f -> ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[1].getTypeName()));
    }
}

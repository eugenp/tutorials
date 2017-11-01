package org.baeldung.persistence.dao;

import org.baeldung.persistence.model.Address_;
import org.baeldung.persistence.model.User;
import org.baeldung.persistence.model.User_;
import org.baeldung.web.util.SearchOperation;
import org.baeldung.web.util.SpecSearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public final class UserSpecificationsBuilder {

    private final List<SpecSearchCriteria> params;
    private boolean joinAddress;

    public UserSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    // API

    public final UserSpecificationsBuilder with(final String key, final String operation, final Comparable value, final String prefix, final String suffix) {
        return with(null, key, operation, value, prefix, suffix, false);
    }

    public final UserSpecificationsBuilder with(final String key, final String operation, final Comparable value, final String prefix, final String suffix, final boolean joinAddress) {
        return with(null, key, operation, value, prefix, suffix, joinAddress);
    }

    public final UserSpecificationsBuilder with(final String orPredicate, final String key, final String operation, final Comparable value, final String prefix, final String suffix) {
        return with(orPredicate, key, operation, value, prefix, suffix, false);
    }

    public final UserSpecificationsBuilder with(final String orPredicate, final String key, final String operation, final Comparable value, final String prefix, final String suffix, final boolean joinAddress) {
        this.joinAddress = joinAddress;
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
        SpecSearchCriteria criteria = checkParamsAndAddPrefix(params.get(0), userAtts, addressAtts);
        Specification<User> result = new UserSpecification(criteria, joinAddress);

        for (int i = 1; i < params.size(); i++) {
            criteria = checkParamsAndAddPrefix(params.get(i), userAtts, addressAtts);
            result = criteria
                    .isOrPredicate()
                    ? Specifications.where(result)
                    .or(new UserSpecification(criteria))
                    : Specifications.where(result)
                    .and(new UserSpecification(criteria));

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

    private SpecSearchCriteria checkParamsAndAddPrefix(SpecSearchCriteria criteria, Map<String, String> contractAtts, Map<String, String> signatureAtts) {
        String userAttType = contractAtts.get(criteria.key());
        if (nonNull(userAttType)) {
            Comparable value = convertValue(criteria.value(), userAttType);
            return new SpecSearchCriteria().key(criteria.key())
                    .operation(criteria.operation())
                    .value(value)
                    .orPredicate(criteria.isOrPredicate());
        }
        String addressAttType = signatureAtts.get(criteria.key());
        if (nonNull(addressAttType)) {
            Comparable value = convertValue(criteria.value(), addressAttType);
            String key = User_.address.getName() + "." + criteria.key();
            return new SpecSearchCriteria().key(key)
                    .operation(criteria.operation())
                    .value(value)
                    .orPredicate(criteria.isOrPredicate());
        }
        throw new IllegalArgumentException(criteria.key() + " does not match any entity attribute.");
    }

    private Comparable convertValue(Object value, String type) {
        try {
            if (String.class.getName().equals(type)) {
                return value.toString();
            }
            if (Boolean.class.getName().equals(type)) {
                return Boolean.valueOf(value.toString());
            }
            if (LocalDate.class.getName().equals(type)) {
                return LocalDate.parse(value.toString(), DateTimeFormatter.ofPattern("yyyyMMdd").withLocale(Locale.getDefault()));
            }
            return value.toString();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Cannot convert [" + value.toString() + "] to required type [" + type + "] : " +
                    e.getLocalizedMessage());
        }
    }

    public UserSpecificationsBuilder joinAddress() {
        this.joinAddress = true;
        return this;
    }
}

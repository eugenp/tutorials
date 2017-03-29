package org.baeldung.persistence.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.baeldung.web.util.SearchOperation;
import org.baeldung.web.util.SpecSearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

public class GenericSpecificationsBuilder<U> {

    private final List<SpecSearchCriteria> params;

    public GenericSpecificationsBuilder() {
        this.params = new ArrayList<>();
    }

    public final GenericSpecificationsBuilder<U> with(final String key, final String operation, final Object value, final String prefix, final String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public final GenericSpecificationsBuilder<U> with(final String precedenceIndicator, final String key, final String operation, final Object value, final String prefix, final String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) // the operation may be complex operation
            {
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
            params.add(new SpecSearchCriteria(precedenceIndicator, key, op, value));
        }
        return this;
    }

    public Specification<U> build(Function<SpecSearchCriteria, Specification<U>> converter) {

        if (params.size() == 0) {
            return null;
        }

        params.sort(Comparator.comparing(SpecSearchCriteria::isOrPredicate));

        final List<Specification<U>> specs = params.stream()
            .map(converter)
            .collect(Collectors.toCollection(ArrayList::new));

        Specification<U> result = specs.get(0);

        for (int idx = 1; idx < specs.size(); idx++) {
            result = params.get(idx)
                .isOrPredicate()
                    ? Specifications.where(result)
                        .or(specs.get(idx))
                    : Specifications.where(result)
                        .and(specs.get(idx));
        }
        return result;
    }

    public Specification<U> build(Deque<?> postFixedExprStack, Function<SpecSearchCriteria, Specification<U>> converter) {

        Deque<Specification<U>> operandStack = new LinkedList<>();

        Collections.reverse((List<?>) postFixedExprStack);

        while (!postFixedExprStack.isEmpty()) {
            Object mayBeOperand = postFixedExprStack.pop();

            if (!(mayBeOperand instanceof String)) {
                operandStack.push(converter.apply((SpecSearchCriteria) mayBeOperand));
            } else {
                Specification<U> operand1 = operandStack.pop();
                Specification<U> operand2 = operandStack.pop();
                if (mayBeOperand.equals(SearchOperation.AND_OPERATOR))
                    operandStack.push(Specifications.where(operand1)
                        .and(operand2));
                else if (mayBeOperand.equals(SearchOperation.OR_OPERATOR))
                    operandStack.push(Specifications.where(operand1)
                        .or(operand2));
            }

        }
        return operandStack.pop();

    }

}

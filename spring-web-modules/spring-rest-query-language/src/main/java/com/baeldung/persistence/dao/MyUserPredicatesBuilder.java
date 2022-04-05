package com.baeldung.persistence.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.baeldung.web.util.SearchCriteria;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

public final class MyUserPredicatesBuilder {
    private final List<SearchCriteria> params;

    public MyUserPredicatesBuilder() {
        params = new ArrayList<>();
    }

    public MyUserPredicatesBuilder with(final String key, final String operation, final Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public BooleanExpression build() {
        if (params.size() == 0) {
            return null;
        }
        
        final List<BooleanExpression> predicates = params.stream().map(param -> {
            MyUserPredicate predicate = new MyUserPredicate(param);
            return predicate.getPredicate();
        }).filter(Objects::nonNull).collect(Collectors.toList());
        
        BooleanExpression result = Expressions.asBoolean(true).isTrue();
        for (BooleanExpression predicate : predicates) {
            result = result.and(predicate);
        }
        
        return result;
    }
    
    static class BooleanExpressionWrapper {
        
        private BooleanExpression result;

        public BooleanExpressionWrapper(final BooleanExpression result) {
            super();
            this.result = result;
        }

        public BooleanExpression getResult() {
            return result;
        }
        public void setResult(BooleanExpression result) {
            this.result = result;
        }
    }
}

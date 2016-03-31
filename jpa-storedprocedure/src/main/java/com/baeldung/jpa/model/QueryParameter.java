package com.baeldung.jpa.model;

import java.util.HashMap;
import java.util.Map;

public class QueryParameter {

    private Map<String, Object> parameters = null;

    private QueryParameter(final String name, final Object value) {
        this.parameters = new HashMap<>();
        this.parameters.put(name, value);
    }

    public static QueryParameter with(final String name, final Object value) {
        return new QueryParameter(name, value);
    }

    public QueryParameter and(final String name, final Object value) {
        this.parameters.put(name, value);
        return this;
    }

    public Map<String, Object> parameters() {
        return this.parameters;
    }

}
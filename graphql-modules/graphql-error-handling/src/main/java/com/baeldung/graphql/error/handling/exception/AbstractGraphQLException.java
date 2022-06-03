package com.baeldung.graphql.error.handling.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbstractGraphQLException extends RuntimeException implements GraphQLError {
    private Map<String, Object> parameters = new HashMap();

    public AbstractGraphQLException(String message) {
        super(message);
    }

    public AbstractGraphQLException(String message, Map<String, Object> additionParams) {
        this(message);
        if (additionParams != null) {
            parameters = additionParams;
        }
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return this.parameters;
    }
}

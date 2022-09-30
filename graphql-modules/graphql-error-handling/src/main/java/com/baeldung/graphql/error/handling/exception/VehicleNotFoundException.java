package com.baeldung.graphql.error.handling.exception;

import java.util.Map;

public class VehicleNotFoundException extends AbstractGraphQLException {

    public VehicleNotFoundException(String message) {
        super(message);
    }

    public VehicleNotFoundException(String message, Map<String, Object> params) {
        super(message, params);
    }
}

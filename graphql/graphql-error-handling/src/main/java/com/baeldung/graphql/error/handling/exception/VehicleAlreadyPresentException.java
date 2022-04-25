package com.baeldung.graphql.error.handling.exception;

import java.util.Map;

public class VehicleAlreadyPresentException extends AbstractGraphQLException {

     public VehicleAlreadyPresentException(String message) {
         super(message);
     }

    public VehicleAlreadyPresentException(String message, Map<String, Object> additionParams) {
        super(message, additionParams);
    }
}

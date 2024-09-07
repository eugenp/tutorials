package com.baeldung.synchronous.gateway;

public class ApiGatewayException extends Exception {

    public ApiGatewayException(String message) {
        super(message);
    }
}

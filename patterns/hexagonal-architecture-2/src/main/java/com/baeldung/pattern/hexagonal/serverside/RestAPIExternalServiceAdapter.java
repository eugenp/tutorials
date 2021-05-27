package com.baeldung.pattern.hexagonal.serverside;


import com.baeldung.pattern.hexagonal.domain.ExternalServicePort;

public class RestAPIExternalServiceAdapter implements ExternalServicePort {
    @Override
    public String fetchSomeDataFromExternalService() {
        return "REST API based adapter";
    }
}

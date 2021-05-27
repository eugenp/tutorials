package com.baeldung.pattern.hexagonal.serverside;


import com.baeldung.pattern.hexagonal.domain.ExternalServicePort;

public class FileBasedExternalServiceAdapter implements ExternalServicePort {
    @Override
    public String fetchSomeDataFromExternalService() {
        return "file Based adapter";
    }
}

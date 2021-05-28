package com.baeldung.pattern.hexagonal.domain;

public class BusinessLogic implements UIActionPort {

    private final ExternalServicePort externalServicePort;

    public BusinessLogic(ExternalServicePort externalServicePort) {
        this.externalServicePort = externalServicePort;
    }

    @Override
    public String executeSomeAction(String context) {
        return "Called from the " + context + " context. Fetched from "
          + externalServicePort.fetchSomeDataFromExternalService();
    }
}

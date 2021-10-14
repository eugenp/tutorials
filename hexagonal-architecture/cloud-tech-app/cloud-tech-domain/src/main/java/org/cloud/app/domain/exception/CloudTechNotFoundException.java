package org.cloud.app.domain.exception;

public class CloudTechNotFoundException extends RuntimeException {

    public CloudTechNotFoundException(Long id) {
        super("Cloud Tech with id %s not found!".formatted(id));
    }
}

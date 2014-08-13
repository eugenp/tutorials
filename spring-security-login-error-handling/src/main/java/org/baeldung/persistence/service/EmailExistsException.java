package org.baeldung.persistence.service;

public class EmailExistsException extends Throwable {

    public EmailExistsException(String message) {
        super(message);
    }
}

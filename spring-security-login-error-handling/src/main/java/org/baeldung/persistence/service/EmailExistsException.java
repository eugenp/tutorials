package org.baeldung.persistence.service;

@SuppressWarnings("serial")
public class EmailExistsException extends Throwable {

    public EmailExistsException(String message) {
        super(message);
    }
}

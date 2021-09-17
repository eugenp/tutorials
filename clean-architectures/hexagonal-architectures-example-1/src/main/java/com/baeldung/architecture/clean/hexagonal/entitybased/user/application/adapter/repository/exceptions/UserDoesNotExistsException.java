package com.baeldung.architecture.clean.hexagonal.entitybased.user.application.adapter.repository.exceptions;

import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.port.repository.exceptions.UserRepositoryException;

public class UserDoesNotExistsException extends UserRepositoryException {
    public UserDoesNotExistsException() {
        super();
    }
}

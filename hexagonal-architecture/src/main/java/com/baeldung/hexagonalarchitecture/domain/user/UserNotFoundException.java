package com.baeldung.hexagonalarchitecture.domain.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception represents when a {@link User} not found in database
 *
 * @author José Carlos Mazella Junior
 * @version 1.0 10/12/2018
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final String message) {
        super(message);
    }

}

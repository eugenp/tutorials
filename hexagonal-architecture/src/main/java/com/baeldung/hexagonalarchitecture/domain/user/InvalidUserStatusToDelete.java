package com.baeldung.hexagonalarchitecture.domain.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception represents an invalid status to delete user
 *
 * @author José Carlos Mazella Junior
 * @version 1.0 10/12/2018
 */
@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class InvalidUserStatusToDelete extends RuntimeException {

    public InvalidUserStatusToDelete(final String message) {
        super(message);
    }

}

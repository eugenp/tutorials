package org.baeldung.web.controller.status;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "To show an example of a custom message")
class ForbiddenException extends RuntimeException {
    private static final long serialVersionUID = 6826605655586311552L;

}

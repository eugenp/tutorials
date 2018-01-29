package com.baeldung.execption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ActorNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public ActorNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}

class ActressNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public ActressNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Actor Not Found")
class LeadActorNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public LeadActorNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Actress Not Found")
class LeadActressNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public LeadActressNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}

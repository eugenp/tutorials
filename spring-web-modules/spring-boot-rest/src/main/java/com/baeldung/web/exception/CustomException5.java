package com.baeldung.web.exception;

import java.io.Serial;

public class CustomException5 extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CustomException5(String message) {
        super(message);
    }
}

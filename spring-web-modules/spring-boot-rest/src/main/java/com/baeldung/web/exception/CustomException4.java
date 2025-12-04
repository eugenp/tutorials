package com.baeldung.web.exception;

import java.io.Serial;

public class CustomException4 extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CustomException4(String message) {
        super(message);
    }
}

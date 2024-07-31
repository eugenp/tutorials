package com.baeldung.demo.exceptions;

public class CommonException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 3080004140659213332L;

    public CommonException(String message) {
        super(message);
    }
}

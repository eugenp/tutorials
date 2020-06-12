package com.baeldung.hexagonal.domain;

public class ShortenerException extends RuntimeException {

    private ErrorTypeEnum errorTypeEnum;

    public ShortenerException(ErrorTypeEnum errorType) {
        super(errorType.getValue());
        this.errorTypeEnum = errorType;
    }

    public ErrorTypeEnum getErrorTypeEnum() {
        return errorTypeEnum;
    }
}

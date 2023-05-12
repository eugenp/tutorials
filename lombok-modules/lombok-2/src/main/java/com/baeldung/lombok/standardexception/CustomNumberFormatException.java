package com.baeldung.lombok.standardexception;

public class CustomNumberFormatException extends NumberFormatException {
    public CustomNumberFormatException() {
        super();
    }

    public CustomNumberFormatException(String s) {
        super(s);
    }
}
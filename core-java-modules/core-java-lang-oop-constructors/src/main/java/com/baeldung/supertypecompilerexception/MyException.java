package com.baeldung.supertypecompilerexception;

public class MyException extends RuntimeException {
    private int errorCode = 0;

    public MyException(String message) {
        //uncomment this to see the supertype compiler error:
        //super(message + getErrorCode());
    }

    public int getErrorCode() {
        return errorCode;
    }
}

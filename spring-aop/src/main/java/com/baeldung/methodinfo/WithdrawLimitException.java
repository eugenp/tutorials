package com.baeldung.methodinfo;

public class WithdrawLimitException extends RuntimeException {
    public WithdrawLimitException(String message) {
        super(message);
    }
}

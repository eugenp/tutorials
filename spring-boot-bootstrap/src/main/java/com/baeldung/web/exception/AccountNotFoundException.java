package com.baeldung.web.exception;
/*
 * created by pareshP on 03/04/19
 */

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException() {
        super();
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}

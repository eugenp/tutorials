package com.baeldung.continuetransactionafterexception;

public class NotificationSendingException extends RuntimeException {

    public NotificationSendingException(String text) {
        super(text);
    }
}

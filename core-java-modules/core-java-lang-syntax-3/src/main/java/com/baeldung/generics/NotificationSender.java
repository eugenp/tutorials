package com.baeldung.generics;

public class NotificationSender<T> implements Sender {

    private T body;

    public NotificationSender() {
    }

    public NotificationSender(final T body) {
        this.body = body;
    }

    @Override
    public String send() {
        return "NOTIFICATION";
    }

    public T getBody() {
        return body;
    }
}

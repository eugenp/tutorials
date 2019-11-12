package com.baeldung.hexagonal.architecture.holiday.port;

@FunctionalInterface
public interface Notifier {

    void sendMessage(String recipient, String message);
}

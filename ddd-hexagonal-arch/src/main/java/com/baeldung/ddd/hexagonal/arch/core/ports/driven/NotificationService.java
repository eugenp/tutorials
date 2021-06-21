package com.baeldung.ddd.hexagonal.arch.core.ports.driven;

public interface NotificationService {
    void sendMessage(String to, String message);
}

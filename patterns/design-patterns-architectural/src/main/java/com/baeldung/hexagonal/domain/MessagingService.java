package com.baeldung.hexagonal.domain;

public interface MessagingService {

    void publishMessage(Object message);

}

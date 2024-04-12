package com.baeldung.seda.springintegration;

import java.util.Map;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface TestGateway {
    @Gateway(requestChannel = "receiveTextChannel", replyChannel = "returnResponseChannel")
    public Map<String, Long> countWords(String test);
}

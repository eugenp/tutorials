package com.baeldung.spring.mvc;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HelloWorldEventLogger {

    @Async
    @EventListener
    void logHelloWorldEvent(HelloWorldEvent event) {
        log.info("Hello World Event: {}", event.message());
    }

}

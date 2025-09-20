package com.baeldung.spring.mvc;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/hello", version = "4")
@RequiredArgsConstructor
public class HelloWorldV4Controller {

    private final ChristmasJoyClient christmasJoy;
    private final ApplicationEventPublisher applicationEventPublisher;

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String sayHello() {
        final var result = this.christmasJoy.getRandomGreeting();
        applicationEventPublisher.publishEvent(new HelloWorldEvent(result));
        return result;
    }

}

package com.baeldung.spring.cloud.hystrix.rest.producer;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingControllerImpl implements GreetingController {
    @Override
    public String greeting(@PathVariable("username") String username) {
        return String.format("Hello %s!\n", username);
    }
}

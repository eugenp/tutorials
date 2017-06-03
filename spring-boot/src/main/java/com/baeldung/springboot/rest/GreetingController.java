package com.baeldung.springboot.rest;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
@Component
public class GreetingController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/greetings", method = RequestMethod.GET)
    public Greeting greeting() {
        
        return new Greeting(new Integer((int) counter.incrementAndGet()));
    }
}
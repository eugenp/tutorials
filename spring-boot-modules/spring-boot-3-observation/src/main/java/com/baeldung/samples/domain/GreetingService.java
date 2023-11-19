package com.baeldung.samples.domain;

import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Service;

@Observed(name = "greetingService")
@Service
public class GreetingService {

    public String sayHello() {
        return "Hello World!";
    }

}

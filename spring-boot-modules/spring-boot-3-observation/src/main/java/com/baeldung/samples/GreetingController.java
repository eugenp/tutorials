package com.baeldung.samples;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/greet")
public class GreetingController {

    private final ObservationRegistry registry;

    public GreetingController(ObservationRegistry registry) {
        this.registry = registry;
    }

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String sayHello() {
        return "Hello World \n"
          + this.registry.getCurrentObservation() + "\n";
    }

}

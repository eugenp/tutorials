package com.baeldung.samples.boundary;

import com.baeldung.samples.domain.GreetingService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/greet")
public class GreetingController {

    private final GreetingService service;

    public GreetingController(GreetingService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String sayHello() {
        return this.service.sayHello();
    }

}

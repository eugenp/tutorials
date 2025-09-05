package com.baeldung.spring.mvc;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {

    @GetMapping(version = "1", produces = MediaType.TEXT_PLAIN_VALUE)
    public String sayHelloV1() {
        return "Hello World";
    }

    @GetMapping(version = "2", produces = MediaType.TEXT_PLAIN_VALUE)
    public String sayHelloV2() {
        return "Hi World";
    }
 
}

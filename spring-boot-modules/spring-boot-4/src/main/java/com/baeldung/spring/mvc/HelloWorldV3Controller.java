package com.baeldung.spring.mvc;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/hello", version = "3")
public class HelloWorldV3Controller {

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String sayHello() {
        return "Hey World";
    }

}

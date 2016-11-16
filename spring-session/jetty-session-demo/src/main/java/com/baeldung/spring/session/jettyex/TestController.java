package com.baeldung.spring.session.jettyex;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping
    public String helloJetty() {
        return "hello Jetty";
    }
}
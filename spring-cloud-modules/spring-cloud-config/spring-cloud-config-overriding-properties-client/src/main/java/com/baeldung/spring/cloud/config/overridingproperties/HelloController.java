package com.baeldung.spring.cloud.config.overridingproperties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${hello}")
    private String hello;

    @Value("${welcome}")
    private String welcome;

    @GetMapping("hello")
    public String hello() {
        return this.hello;
    }

    @GetMapping("welcome")
    public String welcome() {
        return this.welcome;
    }
}

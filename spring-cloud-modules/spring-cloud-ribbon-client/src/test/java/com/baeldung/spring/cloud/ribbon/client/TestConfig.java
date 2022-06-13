package com.baeldung.spring.cloud.ribbon.client;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableAutoConfiguration
@RestController
public class TestConfig {

    @RequestMapping(value = "/locaus")
    public String locationAUSDetails() {
        return "Australia";
    }
}

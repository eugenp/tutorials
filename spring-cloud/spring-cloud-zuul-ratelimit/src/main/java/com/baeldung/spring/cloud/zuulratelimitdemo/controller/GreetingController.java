package com.baeldung.spring.cloud.zuulratelimitdemo.controller;

import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@EnableZuulProxy
@SpringCloudApplication
@RequestMapping("/greeting")
public class GreetingController {

    public static final String SIMPLE_RESPONSE = "Hi!";
    public static final String ADVANCED_RESPONSE = "Hello, how you doing?";

    @GetMapping("/simple")
    public ResponseEntity<String> getSimple() {
        return ResponseEntity.ok(SIMPLE_RESPONSE);
    }

    @GetMapping("/advanced")
    public ResponseEntity<String> getAdvanced() {
        return ResponseEntity.ok(ADVANCED_RESPONSE);
    }
}

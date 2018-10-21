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

  @GetMapping("/simple")
  public ResponseEntity<String> serviceA() {
    return ResponseEntity.ok("Hi!");
  }

  @GetMapping("/advanced")
  public ResponseEntity<String> serviceB() {
    return ResponseEntity.ok("Hello, how you doing?");
  }
}

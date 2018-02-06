package com.baeldung.spring.cloud.springcloudcontractproducer.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EvenOddController {

  @GetMapping("/validate/prime-number")
  public String isNumberPrime(@RequestParam("number") String number) {
    return Integer.parseInt(number) % 2 == 0 ? "Even" : "Odd";
  }
}

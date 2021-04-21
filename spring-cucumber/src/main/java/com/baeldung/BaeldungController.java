package com.baeldung;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaeldungController {

    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @PostMapping("/baeldung")
    public String sayHelloPost() {
        return "hello";
    }
}

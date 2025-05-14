package com.baeldung.springbootssl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")
public class SecureController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Secure World!";
    }

}

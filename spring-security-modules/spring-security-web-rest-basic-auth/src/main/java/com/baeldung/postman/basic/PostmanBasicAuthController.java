package com.baeldung.postman.basic;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostmanBasicAuthController {

    @GetMapping("postman-test")
    public String test() {
        return "request was authorized!";
    }

}

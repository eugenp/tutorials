package com.baeldung.spring.aotrepository.web;

import static org.springframework.http.ResponseEntity.ok;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public ResponseEntity<String> hello() {
        return ok("hello back");
    }
}

package com.baeldung.spring.boot.management.trace;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("echo")
public class EchoController {

    @GetMapping
    public String echo(@RequestParam("msg") String msg) {
        return "echoing " + msg;
    }
    
}

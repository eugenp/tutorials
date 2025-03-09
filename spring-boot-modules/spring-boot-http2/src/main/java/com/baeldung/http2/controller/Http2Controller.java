package com.baeldung.http2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Http2Controller {

    @GetMapping("/http2/echo")
    public String getEcho(@RequestParam String message) {
        return message;
    }

}
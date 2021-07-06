package com.baeldung.maxhttpheadersize.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/request-header-test")
public class MaxHttpHeaderSizeController {

    @GetMapping
    public boolean testMaxHTTPHeaderSize(@RequestHeader(value = "token") String token) {
        return true;
    }

}

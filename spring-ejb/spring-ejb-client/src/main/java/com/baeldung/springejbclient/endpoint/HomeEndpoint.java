package com.baeldung.springejbclient.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeEndpoint {

    @GetMapping("/")
    public String getResponse() {
        return "teste";
    }

}

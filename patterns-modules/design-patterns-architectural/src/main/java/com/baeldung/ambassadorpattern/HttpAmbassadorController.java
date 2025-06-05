package com.baeldung.ambassadorpattern;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/http-ambassador/names")
public class HttpAmbassadorController {

    private final HttpAmbassadorNamesApiClient httpAmbassadorNamesApiClient;

    public HttpAmbassadorController(HttpAmbassadorNamesApiClient httpAmbassadorNamesApiClient) {
        this.httpAmbassadorNamesApiClient = httpAmbassadorNamesApiClient;
    }

    @GetMapping
    public String get() {
        return httpAmbassadorNamesApiClient.getResponse();
    }
}

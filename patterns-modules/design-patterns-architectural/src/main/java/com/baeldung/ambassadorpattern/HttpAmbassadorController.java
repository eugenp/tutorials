package com.baeldung.ambassadorpattern;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/http-ambassador")
public class HttpAmbassadorController {

    private final HttpAmbassadorClient httpAmbassadorClient;

    public HttpAmbassadorController(HttpAmbassadorClient httpAmbassadorClient) {
        this.httpAmbassadorClient = httpAmbassadorClient;
    }

    @GetMapping("/get/execute")
    public String get(@RequestBody HttpAmbassadorRequest request) {
        return httpAmbassadorClient.getResponse(request.getUri(), request.getQueryParams());
    }
}

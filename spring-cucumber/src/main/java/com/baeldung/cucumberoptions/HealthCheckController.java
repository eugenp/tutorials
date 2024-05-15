package com.baeldung.cucumberoptions;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping(path = "/v1/status", produces = APPLICATION_JSON_VALUE)
    public HttpStatusCode getV1Status() {
        return ResponseEntity.ok().build().getStatusCode();
    }

    @GetMapping(path = "/v2/status", produces = APPLICATION_JSON_VALUE)
    public HttpStatusCode getV2Status() {
        return ResponseEntity.ok().build().getStatusCode();
    }
}
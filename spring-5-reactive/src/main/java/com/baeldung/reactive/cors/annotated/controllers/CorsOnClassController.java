package com.baeldung.reactive.cors.annotated.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@CrossOrigin(value = { "http://allowed-origin.com" }, allowedHeaders = { "Baeldung-Another-Allowed" }, maxAge = 900)
@RestController
@RequestMapping("/cors-on-controller")
public class CorsOnClassController {

    @PutMapping("/regular-endpoint")
    public Mono<String> corsDisabledEndpoint() {
        return Mono.just("Regular endpoint");
    }

    @CrossOrigin
    @PutMapping("/cors-enabled-endpoint")
    public Mono<String> corsEnabledEndpoint() {
        return Mono.just("CORS enabled endpoint");
    }

    @CrossOrigin({ "http://another-allowed-origin.com" })
    @PutMapping("/cors-enabled-origin-restrictive-endpoint")
    public Mono<String> corsEnabledOriginRestrictiveEndpoint() {
        return Mono.just("CORS enabled endpoint - Origin Restrictive");
    }

    @CrossOrigin(allowedHeaders = { "Baeldung-Allowed" })
    @PutMapping("/cors-enabled-header-restrictive-endpoint")
    public Mono<String> corsEnabledHeaderRestrictiveEndpoint() {
        return Mono.just("CORS enabled endpoint - Header Restrictive");
    }

    @CrossOrigin(exposedHeaders = { "Baeldung-Exposed" })
    @PutMapping("/cors-enabled-exposed-header-endpoint")
    public Mono<String> corsEnabledExposedHeadersEndpoint() {
        return Mono.just("CORS enabled endpoint - Exposed Header");
    }

    @PutMapping("/cors-enabled-mixed-config-endpoint")
    @CrossOrigin(allowedHeaders = { "Baeldung-Allowed", "Baeldung-Other-Allowed" }, exposedHeaders = { "Baeldung-Allowed", "Baeldung-Exposed" }, maxAge = 3600)
    public Mono<String> corsEnabledHeaderExposedEndpoint() {
        return Mono.just("CORS enabled endpoint - Mixed Config");
    }
}

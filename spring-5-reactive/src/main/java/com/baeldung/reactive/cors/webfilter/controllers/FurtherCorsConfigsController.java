package com.baeldung.reactive.cors.webfilter.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

//@RestController
//@RequestMapping("/web-filter-and-more-on-annotated")
public class FurtherCorsConfigsController {

    @DeleteMapping("/further-mixed-config-endpoint")
    @CrossOrigin(methods = { RequestMethod.DELETE },
        allowedHeaders = { "Baeldung-Other-Allowed" },
        exposedHeaders = { "Baeldung-Other-Exposed" }
    )
    public Mono<String> corsEnabledHeaderExposedEndpoint() {
        final String responseMessage = "CORS @CrossOrigin IS NOT EFFECTIVE with WebFilter!!!";
        
        return Mono.just(responseMessage);
    }

}

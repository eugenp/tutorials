package com.baeldung.webclient;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
public class WebController {

    private static final int DEFAULT_PORT = 8080;

    public static final String SLOW_SERVICE_PRODUCTS_ENDPOINT_NAME = "/slow-service-products";

    @Setter
    private int serverPort = DEFAULT_PORT;

    @Autowired
    private ProductsFeignClient productsFeignClient;

    @GetMapping("/products-blocking")
    public List<Product> getProductsBlocking() {
        log.info("Starting BLOCKING Controller!");
        final URI uri = URI.create(getSlowServiceBaseUri());

        List<Product> result = productsFeignClient.getProductsBlocking(uri);
        result.forEach(product -> log.info(product.toString()));
        log.info("Exiting BLOCKING Controller!");
        return result;
    }

    @GetMapping(value = "/products-non-blocking", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> getProductsNonBlocking() {
        log.info("Starting NON-BLOCKING Controller!");
        Flux<Product> productFlux = WebClient.create()
          .get()
          .uri(getSlowServiceBaseUri() + SLOW_SERVICE_PRODUCTS_ENDPOINT_NAME)
          .retrieve()
          .bodyToFlux(Product.class);

        productFlux.subscribe(product -> log.info(product.toString()));
        log.info("Exiting NON-BLOCKING Controller!");
        return productFlux;
    }

    private String getSlowServiceBaseUri() {
        return "http://localhost:" + serverPort;
    }

}

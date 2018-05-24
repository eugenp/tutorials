package com.baeldung.functional.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.baeldung.functional.model.CityAirport;

import reactor.core.publisher.Mono;

@RestController("/api/v2")
public class NearestAirportController {

    @GetMapping("/city")
    public Mono<ServerResponse> handleGetAirports() {
        return WebClient.create("http://localhost:9000").get().uri("/api/city")
                .accept(MediaType.APPLICATION_JSON).exchange().flatMap(resp -> ServerResponse.ok().body(resp.bodyToFlux(CityAirport.class), CityAirport.class));
    }

    @GetMapping("/city/{city}")
    public Mono<ServerResponse> handleGetAirportByCityName(@PathVariable String cityName) {
        return WebClient.create("http://localhost:9000").get().uri("/api/city/" + cityName)
                .accept(MediaType.APPLICATION_JSON).exchange().flatMap(resp -> ServerResponse.ok().body(resp.bodyToMono(CityAirport.class), CityAirport.class));
    }

}

package com.baeldung.functional.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.baeldung.functional.model.CityAirport;
import com.baeldung.functional.repository.FurthestAirportRepository;

import reactor.core.publisher.Mono;

@Service
public class FurthestAirportHandler {

    @Autowired
    private FurthestAirportRepository cityAirportRepository;

    public Mono<ServerResponse> handleGetCityAirports(ServerRequest request) {
        return ServerResponse.ok().body(cityAirportRepository.getCityAirports(), CityAirport.class);
    }

    public Mono<ServerResponse> handleGetAirportByCityName(ServerRequest request) {
        return cityAirportRepository.getAirportByCity(request.pathVariable("city"))
                .flatMap(user -> ServerResponse.ok().body(Mono.just(user), CityAirport.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}

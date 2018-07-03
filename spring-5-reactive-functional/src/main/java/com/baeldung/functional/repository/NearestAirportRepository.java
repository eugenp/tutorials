package com.baeldung.functional.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.baeldung.functional.model.CityAirport;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Annotation based bean registration
 */
@Repository
public class NearestAirportRepository {

    private final List<CityAirport> cityNameAirports = Arrays.asList(new CityAirport("New York", "JFK"), new CityAirport("Kolkata", "NSCBI"));

    public Mono<CityAirport> getAirportByCity(String cityName) {
        return Mono.justOrEmpty(cityNameAirports.stream().filter(user -> {
            return user.getCityName().equals(cityName);
        }).findFirst().orElse(null));
    }

    public Flux<CityAirport> getCityAirports() {
        return Flux.fromIterable(cityNameAirports);
    }

}

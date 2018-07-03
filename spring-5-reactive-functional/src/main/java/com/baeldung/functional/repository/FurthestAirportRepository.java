package com.baeldung.functional.repository;

import java.util.List;

import com.baeldung.functional.model.CityAirport;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Functional bean registration
 */
public class FurthestAirportRepository {

    private List<CityAirport> cityNameAirports;
    
    public FurthestAirportRepository(List<CityAirport> cityNameAirports) {
        this.cityNameAirports = cityNameAirports;
    }

    public Mono<CityAirport> getAirportByCity(String cityName) {
        return Mono.justOrEmpty(cityNameAirports.stream().filter(user -> {
            return user.getCityName().equals(cityName);
        }).findFirst().orElse(null));
    }

    public Flux<CityAirport> getCityAirports() {
        return Flux.fromIterable(cityNameAirports);
    }
}

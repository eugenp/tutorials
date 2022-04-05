package com.baeldung.spring_project.domain;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ZIPRepo extends ReactiveCrudRepository<ZipCode, String> {

    @Query("SELECT * FROM zipcode WHERE city = :city")
    Flux<ZipCode> findByCity(String city);
}

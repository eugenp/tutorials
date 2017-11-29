package com.baeldung.spring.data.reactive.repo;

import com.baeldung.spring.data.reactive.domain.Taxi;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by shazi on 11/20/2017.
 */
public interface ReactiveTaxiRepository extends ReactiveMongoRepository<Taxi, String> {

    Flux<Taxi> findByNumber(String number);

    @Tailable
    Flux<Taxi> findWithTailableCursorBy();

}

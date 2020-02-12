package com.baeldung.tailablecursor.repository;

import com.baeldung.tailablecursor.domain.Log;
import com.baeldung.tailablecursor.domain.LogLevel;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface LogsRepository extends ReactiveCrudRepository<Log, String> {
    @Tailable
    Flux<Log> findByLevel(LogLevel level);
}

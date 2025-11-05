package com.baeldung.couchbase.domain.repository.n1ql;

import com.baeldung.couchbase.domain.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface N1QLPersonRepository extends ReactiveCrudRepository<Person, UUID> {

    Flux<Person> findAllByFirstName(final String firstName);
}
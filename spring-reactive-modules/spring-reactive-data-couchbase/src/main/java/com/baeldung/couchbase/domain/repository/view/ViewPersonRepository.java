package com.baeldung.couchbase.domain.repository.view;

import com.baeldung.couchbase.domain.Person;
import org.springframework.data.couchbase.core.query.View;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
@ViewIndexed(designDoc = ViewPersonRepository.DESIGN_DOCUMENT)
public interface ViewPersonRepository extends ReactiveCrudRepository<Person, UUID> {

    String DESIGN_DOCUMENT = "person";

    @View(designDocument = ViewPersonRepository.DESIGN_DOCUMENT)
    Flux<Person> findByFirstName(String firstName);
}
package com.baeldung.couchbase.domain.repository.view;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.couchbase.configuration.CouchbaseProperties;
import com.baeldung.couchbase.configuration.ViewReactiveCouchbaseConfiguration;
import com.baeldung.couchbase.domain.Person;
import com.baeldung.couchbase.domain.repository.CouchbaseMockConfiguration;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = { "spring.couchbase.port=10010", "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration,org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration" },
                classes = { CouchbaseMockConfiguration.class, ViewReactiveCouchbaseConfiguration.class, CouchbaseProperties.class })
public class ViewPersonRepositoryIntegrationTest {

    @Autowired private ViewPersonRepository personRepository;

    @Test
    public void shouldSavePerson_findById_thenDeleteIt() {
        //Given
        final UUID id = UUID.randomUUID();
        final Person person = new Person(id, "John");
        wrap(() -> {
            personRepository
              .save(person)
              .subscribe();
            //When
            final Mono<Person> byId = personRepository.findById(id);
            //Then
            StepVerifier
              .create(byId)
              .expectNextMatches(result -> result
                .getId()
                .equals(id))
              .expectComplete()
              .verify();
        }, person);
    }

    @Test
    public void shouldFindAll_thenDeleteIt() {
        //Given
        final Person person = new Person(UUID.randomUUID(), "John");
        final Person secondPerson = new Person(UUID.randomUUID(), "Mikki");
        wrap(() -> {
            personRepository
              .save(person)
              .subscribe();
            personRepository
              .save(secondPerson)
              .subscribe();
            //When
            final Flux<Person> all = personRepository.findAll();
            //Then
            StepVerifier
              .create(all)
              .expectNextCount(2)
              .verifyComplete();
        }, person, secondPerson);
    }

    private void wrap(final Runnable runnable, final Person... people) {
        try {
            runnable.run();
        } finally {
            for (final Person person : people) {
                personRepository
                  .delete(person)
                  .subscribe();
            }
        }
    }
}
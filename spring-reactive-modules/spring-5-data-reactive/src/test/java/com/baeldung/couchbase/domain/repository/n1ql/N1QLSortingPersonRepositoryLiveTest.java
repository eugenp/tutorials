package com.baeldung.couchbase.domain.repository.n1ql;

import com.baeldung.couchbase.domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration"})
public class N1QLSortingPersonRepositoryLiveTest {

    @Autowired private N1QLSortingPersonRepository personRepository;

    @Test
    public void shouldFindAll_sortedByFirstName() {
        //Given
        final Person firstPerson = new Person(UUID.randomUUID(), "John");
        final Person secondPerson = new Person(UUID.randomUUID(), "Mikki");
        wrap(() -> {
            personRepository
              .save(firstPerson)
              .subscribe();
            personRepository
              .save(secondPerson)
              .subscribe();
            //When
            final Flux<Person> allByFirstName = personRepository.findAll(Sort.by(Sort.Direction.DESC, "firstName"));
            //Then
            StepVerifier
              .create(allByFirstName)
              .expectNextMatches(person -> person
                .getFirstName()
                .equals(secondPerson.getFirstName()))
              .expectNextMatches(person -> person
                .getFirstName()
                .equals(firstPerson.getFirstName()))
              .verifyComplete();
        }, firstPerson, secondPerson);
    }

    //workaround for deleteAll()
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
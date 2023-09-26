package com.baeldung.couchbase.domain.repository.n1ql;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.couchbase.domain.Person;
import com.baeldung.couchbase.domain.repository.n1ql.N1QLPersonRepository;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration"})
public class N1QLPersonRepositoryLiveTest {

    @Autowired private N1QLPersonRepository personRepository;

    @Test
    public void shouldFindAll_byLastName() {
        //Given
        final String firstName = "John";
        final Person matchingPerson = new Person(UUID.randomUUID(), firstName);
        final Person nonMatchingPerson = new Person(UUID.randomUUID(), "NotJohn");
        wrap(() -> {
            personRepository
              .save(matchingPerson)
              .subscribe();
            personRepository
              .save(nonMatchingPerson)
              .subscribe();
            //When
            final Flux<Person> allByFirstName = personRepository.findAllByFirstName(firstName);
            //Then
            StepVerifier
              .create(allByFirstName)
              .expectNext(matchingPerson)
              .verifyComplete();

        }, matchingPerson, nonMatchingPerson);
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
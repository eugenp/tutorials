package com.baeldung.couchbase.domain.repository.n1ql;

import com.baeldung.couchbase.domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
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
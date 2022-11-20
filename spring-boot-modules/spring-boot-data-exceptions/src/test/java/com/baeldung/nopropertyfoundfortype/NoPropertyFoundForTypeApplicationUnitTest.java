package com.baeldung.nopropertyfoundfortype;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class NoPropertyFoundForTypeApplicationUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository repository;

    @Test
    public void givenPersonReposotiory_whenCorrectMapping_thenNoError() {
        entityManager.persist(new Person("Iron", "Man"));
        List<Person> persons = repository.findByLastName("Man");
        assertEquals(1, persons.size());
        assertThat(persons).extracting(Person::getLastName)
            .containsOnly("Man");
    }

    @Test
    public void givenPersonReposotiory_whenNoCorrectMapping_thenNoPropertyFoundForTypeError() {

        entityManager.persist(new Person("Iron", "Man"));
        // uncomment below to get the exception.
        // List<Person> persons = repository.findByLastname("Man");
        // assertEquals(1, persons.size());
        // assertThat(persons).extracting(Person::getLastName).containsOnly("Man");
    }

}

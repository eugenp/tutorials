package com.baeldung.dao.repositories;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.domain.Person;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonRepositoryIntegrationTest {

    private static final Person PERSON1 = new Person(1L, "John", "Doe");
    private static final Person PERSON2 = new Person(2L, "Alice", "Bob");

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void givenPersonEntity_whenInsertWithSave_ThenPersonIsPersisted() {
        personRepository.save(PERSON1);
        assertPersonPersisted(PERSON1);
    }
    
    @Test
    public void givenPersonEntity_whenInsertWithSaveAndFlush_ThenPersonIsPersisted() {
        personRepository.saveAndFlush(PERSON2);
        assertPersonPersisted(PERSON2);
    }

    private void assertPersonPersisted(Person input) {
        Person person = personRepository.getOne(input.getId());
        assertThat(person).isNotNull();
    }
}

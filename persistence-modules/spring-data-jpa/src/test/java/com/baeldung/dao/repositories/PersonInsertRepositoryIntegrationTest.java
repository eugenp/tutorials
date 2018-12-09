package com.baeldung.dao.repositories;

import com.baeldung.config.PersistenceConfiguration;
import com.baeldung.config.PersistenceProductConfiguration;
import com.baeldung.config.PersistenceUserConfiguration;
import com.baeldung.domain.Person;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest(excludeAutoConfiguration = {PersistenceConfiguration.class, PersistenceUserConfiguration.class, PersistenceProductConfiguration.class})
public class PersonInsertRepositoryIntegrationTest {

    private static final Person PERSON = new Person(1l, "firstname", "lastname");
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonCustomRepository personCustomRepository;

    @BeforeEach
    public void clearDB() {
        personRepository.deleteAll();
    }

    @Test
    public void givenPersonEntity_WhenSaveWithNativeQuery_ThenPersonIsPersisted() {
        insertPerson();

        assertPersonPersisted();
    }

    @Test
    public void givenPersonEntityWhenInsertedTwiceWithNativeQueryWithSameIdThenConstraintViolationExceptionIsThrown() {
        assertThatExceptionOfType(DataIntegrityViolationException.class).isThrownBy(() -> {
            insertPerson();
            insertPerson();
        });
    }

    @Test
    public void givenPersonEntityWhenSaveWithQueryAnnotationThenPersonIsPersisted() {
        insertPersonWithQueryAnnotation();

        assertPersonPersisted();
    }

    @Test
    public void givenPersonEntityWhenInsertedTwiceWithQueryAnnotationWithSameIdThenConstraintViolationExceptionIsThrown() {
        assertThatExceptionOfType(DataIntegrityViolationException.class).isThrownBy(() -> {
            insertPersonWithQueryAnnotation();
            insertPersonWithQueryAnnotation();
        });
    }

    @Test
    public void givenPersonEntityWhenSaveWithEntityManagerThenPersonIsPersisted() {
        insertPersonWithEntityManager();

        assertPersonPersisted();
    }

    @Test
    public void givenPersonEntityWhenInsertedTwiceWithEntityManagerWithSameIdThenConstraintViolationExceptionIsThrown() {
        assertThatExceptionOfType(DataIntegrityViolationException.class).isThrownBy(() -> {
            insertPersonWithEntityManager();
            insertPersonWithEntityManager();
        });
    }

    private void insertPerson() {
        personRepository.insert(PERSON);
    }

    private void insertPersonWithQueryAnnotation() {
        personRepository.insertWithAnnotation(1L, "firstname", "lastname");
    }

    private void insertPersonWithEntityManager() {
        personCustomRepository.insert(new Person(1L, "firstname", "lastname"));
    }

    private void assertPersonPersisted() {
        Optional<Person> personOptional = personRepository.findById(PERSON.getId());
        assertThat(personOptional.isPresent()).isTrue();
        assertThat(personOptional.get().getId()).isEqualTo(PERSON.getId());
        assertThat(personOptional.get().getFirstName()).isEqualTo(PERSON.getFirstName());
        assertThat(personOptional.get().getLastName()).isEqualTo(PERSON.getLastName());
    }
}

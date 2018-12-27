package com.baeldung.dao.repositories;

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
@DataJpaTest
public class PersonInsertRepositoryIntegrationTest {

    private static final Long ID = 1L;
    private static final String FIRST_NAME = "firstname";
    private static final String LAST_NAME = "lastname";
    private static final Person PERSON = new Person(ID, FIRST_NAME, LAST_NAME);

    @Autowired
    private PersonQueryRepository personQueryRepository;

    @Autowired
    private PersonEntityManagerRepository personEntityManagerRepository;

    @BeforeEach
    public void clearDB() {
        personQueryRepository.deleteAll();
    }

    @Test
    public void givenPersonEntity_whenInsertWithNativeQuery_ThenPersonIsPersisted() {
        insertPerson();

        assertPersonPersisted();
    }

    @Test
    public void givenPersonEntity_whenInsertedTwiceWithNativeQuery_thenDataIntegrityViolationExceptionIsThrown() {
        assertThatExceptionOfType(DataIntegrityViolationException.class).isThrownBy(() -> {
            insertPerson();
            insertPerson();
        });
    }

    @Test
    public void givenPersonEntity_whenInsertWithQueryAnnotation_thenPersonIsPersisted() {
        insertPersonWithQueryAnnotation();

        assertPersonPersisted();
    }

    @Test
    public void givenPersonEntity_whenInsertedTwiceWithQueryAnnotation_thenDataIntegrityViolationExceptionIsThrown() {
        assertThatExceptionOfType(DataIntegrityViolationException.class).isThrownBy(() -> {
            insertPersonWithQueryAnnotation();
            insertPersonWithQueryAnnotation();
        });
    }

    @Test
    public void givenPersonEntity_whenInsertWithEntityManager_thenPersonIsPersisted() {
        insertPersonWithEntityManager();

        assertPersonPersisted();
    }

    @Test
    public void givenPersonEntity_whenInsertedTwiceWithEntityManager_thenDataIntegrityViolationExceptionIsThrown() {
        assertThatExceptionOfType(DataIntegrityViolationException.class).isThrownBy(() -> {
            insertPersonWithEntityManager();
            insertPersonWithEntityManager();
        });
    }

    private void insertPerson() {
        personQueryRepository.insert(PERSON);
    }

    private void insertPersonWithQueryAnnotation() {
        personQueryRepository.insertWithAnnotation(ID, FIRST_NAME, LAST_NAME);
    }

    private void insertPersonWithEntityManager() {
        personEntityManagerRepository.insert(new Person(ID, FIRST_NAME, LAST_NAME));
    }

    private void assertPersonPersisted() {
        Optional<Person> personOptional = personQueryRepository.findById(PERSON.getId());

        assertThat(personOptional.isPresent()).isTrue();
        assertThat(personOptional.get().getId()).isEqualTo(PERSON.getId());
        assertThat(personOptional.get().getFirstName()).isEqualTo(PERSON.getFirstName());
        assertThat(personOptional.get().getLastName()).isEqualTo(PERSON.getLastName());
    }
}

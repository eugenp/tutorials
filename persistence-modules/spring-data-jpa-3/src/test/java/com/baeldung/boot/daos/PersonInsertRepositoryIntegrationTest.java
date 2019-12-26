package com.baeldung.boot.daos;

import com.baeldung.boot.daos.impl.PersonInsertRepository;
import com.baeldung.boot.domain.Person;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(PersonInsertRepository.class)
public class PersonInsertRepositoryIntegrationTest {

    private static final Long ID = 1L;
    private static final String FIRST_NAME = "firstname";
    private static final String LAST_NAME = "lastname";
    private static final Person PERSON = new Person(ID, FIRST_NAME, LAST_NAME);

    @Autowired
    private PersonInsertRepository personInsertRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void givenPersonEntity_whenInsertWithNativeQuery_ThenPersonIsPersisted() {
        insertWithQuery();

        assertPersonPersisted();
    }

    @Test
    public void givenPersonEntity_whenInsertedTwiceWithNativeQuery_thenPersistenceExceptionExceptionIsThrown() {
        assertThatExceptionOfType(PersistenceException.class).isThrownBy(() -> {
            insertWithQuery();
            insertWithQuery();
        });
    }

    @Test
    public void givenPersonEntity_whenInsertWithEntityManager_thenPersonIsPersisted() {
        insertPersonWithEntityManager();

        assertPersonPersisted();
    }

    @Test
    public void givenPersonEntity_whenInsertedTwiceWithEntityManager_thenEntityExistsExceptionIsThrown() {
        assertThatExceptionOfType(EntityExistsException.class).isThrownBy(() -> {
            insertPersonWithEntityManager();
            insertPersonWithEntityManager();
        });
    }

    private void insertWithQuery() {
        personInsertRepository.insertWithQuery(PERSON);
    }

    private void insertPersonWithEntityManager() {
        personInsertRepository.insertWithEntityManager(new Person(ID, FIRST_NAME, LAST_NAME));
    }

    private void assertPersonPersisted() {
        Person person = entityManager.find(Person.class, ID);

        assertThat(person).isNotNull();
        assertThat(person.getId()).isEqualTo(PERSON.getId());
        assertThat(person.getFirstName()).isEqualTo(PERSON.getFirstName());
        assertThat(person.getLastName()).isEqualTo(PERSON.getLastName());
    }
}

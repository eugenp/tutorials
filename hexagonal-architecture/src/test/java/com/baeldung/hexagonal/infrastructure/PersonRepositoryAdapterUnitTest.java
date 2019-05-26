package com.baeldung.hexagonal.infrastructure;

import com.baeldung.hexagonal.domain.Person;
import com.baeldung.hexagonal.domain.PersonRepositoryPort;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PersonRepositoryAdapterUnitTest {

    @Autowired private PersonRepositoryPort personRepositoryPort;

    @Test
    public void whenSavingPerson_thenSavedToDb() {
        //Given
        final UUID id = UUID.randomUUID();
        final Person person = new Person(id, "name");
        //When
        personRepositoryPort.save(person);
        //Then
        Assert.assertEquals(person, personRepositoryPort
          .findById(id)
          .get());
    }
}
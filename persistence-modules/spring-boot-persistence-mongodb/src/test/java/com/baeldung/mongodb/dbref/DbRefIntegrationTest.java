package com.baeldung.mongodb.dbref;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.mongodb.dbref.model.Person;
import com.baeldung.mongodb.dbref.model.Pet;
import com.baeldung.mongodb.dbref.repository.PersonRepository;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.DBRef;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class DbRefIntegrationTest {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void givenPetsAndPersonInDatabase_whenListPersons_thenReferenceIsFetched() {
        // given
        DBObject catInDatabase = BasicDBObjectBuilder.start()
            .add("name", "Loki")
            .get();

        DBObject dogInDatabase = BasicDBObjectBuilder.start()
            .add("name", "Max")
            .get();

        mongoTemplate.save(catInDatabase, "Cat");
        mongoTemplate.save(dogInDatabase, "Dog");

        List<DBRef> petsReference = new ArrayList<DBRef>();
        petsReference.add(new DBRef("Cat", catInDatabase.get("_id")));
        petsReference.add(new DBRef("Dog", dogInDatabase.get("_id")));

        DBObject personInDatabase = BasicDBObjectBuilder.start()
            .add("name", "Bob")
            .add("pets", petsReference)
            .get();

        mongoTemplate.save(personInDatabase, "Person");

        // when
        List<Person> persons = personRepository.findAll();

        // then
        assertThat(persons).hasSize(1);
        Person person = persons.get(0);
        assertEquals("Bob", person.getName());

        List<Pet> pets = person.getPets();
        assertThat(pets).hasSize(2);
        assertThat(pets).anyMatch(pet -> "Loki".equals(pet.getName()));
        assertThat(pets).anyMatch(pet -> "Max".equals(pet.getName()));
    }

}

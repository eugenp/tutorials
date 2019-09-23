package com.baeldung.hexagonal.test;

import com.baeldung.hexagonal.Person;
import com.baeldung.hexagonal.PersonRegistry;
import com.baeldung.hexagonal.PersonService;
import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@EnableWeld
public class PersonServiceTest {

    @WeldSetup
    public WeldInitiator weld = WeldInitiator
            .from(
                    PersonService.class,
                    ResourcesTest.class
            )
            .build();


    @Test
    void person_service(PersonService service) {
        Person person = new Person();
        person.setSurname("Doe");
        person.setName("John");

        service.persist(person);;

        Assertions.assertNotNull(person);
    }

}

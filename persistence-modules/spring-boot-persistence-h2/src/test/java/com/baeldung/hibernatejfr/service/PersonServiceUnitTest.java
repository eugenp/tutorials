package com.baeldung.hibernatejfr.service;

import com.baeldung.hibernatejfr.HibernateJfrApplication;
import com.baeldung.hibernatejfr.entity.PersonEntity;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = HibernateJfrApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
class PersonServiceUnitTest {

    @Autowired
    PersonService personService;

    Logger logger = LoggerFactory.getLogger(PersonServiceUnitTest.class);

    //@BeforeAll
    void setupDB(){
        PersonEntity entity1 = new PersonEntity();
        entity1.setName("Tom");
        entity1.setDesignation("CFO");
        entity1.setMobile(88888888L);

        PersonEntity entity2 = new PersonEntity();
        entity1.setName("Mark");
        entity1.setDesignation("CFO");
        entity1.setMobile(88888882L);

        personService.createPersons(Arrays.asList(entity1,entity2));

    }

    @Test
    void findPersonById() {
        PersonEntity entity = personService.findPersonById(1L);
        assertNotNull(entity);
        assertEquals(1L,entity.getId());
    }

    @Test
    void updatePerson() {
        PersonEntity updatedEntity=personService.findPersonById(1L);
        updatedEntity.setMobile(7777L);
        updatedEntity.setId(null);
        personService.updatePerson(1L, updatedEntity);
        PersonEntity foundEntity=personService.findPersonById(1L);
        assertEquals(7777L,foundEntity.getMobile());
    }

    @Test
    void createPersons() {
        PersonEntity newEntity = new PersonEntity();
        newEntity.setMobile(9999L);
        newEntity.setName("DoomMarine");
        newEntity.setDesignation("DoomSlayer");

        PersonEntity entity2 = new PersonEntity();
        entity2.setName("Mark");
        entity2.setDesignation("CFO");
        entity2.setMobile(88888882L);

        personService.createPersons(Arrays.asList(newEntity,entity2));

        Long id1 = newEntity.getId();
        PersonEntity foundEntity=personService.findPersonById(id1);
        assertEquals(newEntity.getDesignation(),foundEntity.getDesignation());
        Long id2 = entity2.getId();
        foundEntity=personService.findPersonById(id2);
        assertEquals(entity2.getDesignation(),foundEntity.getDesignation());
    }

    @Test
    void listPersons(){
        List<PersonEntity> personList = personService.listPersons();
        //personService.listPersons().forEach(p -> {logger.info("ID {}",p.getId());});
        assertNotNull(personService.listPersons());
    }

}
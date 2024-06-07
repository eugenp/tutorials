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


    @Test
    void whenIdExists_thenFindPersonById() {
        PersonEntity entity = personService.findPersonById(1L);
        
        assertNotNull(entity);
        assertEquals(1L,entity.getId());
    }

    @Test
    void whenIdExists_thenUpdatePerson() {
        PersonEntity updatedEntity=personService.findPersonById(1L);
        updatedEntity.setMobile(7777L);
        updatedEntity.setId(null);
        personService.updatePerson(1L, updatedEntity);
        PersonEntity foundEntity=personService.findPersonById(1L);
        
        assertEquals(7777L,foundEntity.getMobile());
    }

    @Test
    void whenDatabaseIsEmpty_thenCreatepersons() {
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
    void whenDatabaseHasPersons_thenListPersons(){
        List<PersonEntity> personList = personService.listPersons();
        
        assertNotNull(personService.listPersons());
    }

}

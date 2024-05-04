package com.baeldung.hibernatejfr.service;

import com.baeldung.hibernatejfr.entity.PersonEntity;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql("classpath:test-data.sql")
@TestMethodOrder(MethodOrderer.MethodName.class)
class PersonServiceTest {

    @Autowired
    PersonService personService;

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
        personService.createPersons(List.of(newEntity));
        Long id = newEntity.getId();
        PersonEntity foundEntity=personService.findPersonById(id);
        assertEquals(newEntity.getDesignation(),foundEntity.getDesignation());
    }

    @Test
    void listPersons(){
        assertNotNull(personService.listPersons());
    }

}
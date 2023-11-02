package com.baeldung.modifystream;

import com.baledung.modifystream.entity.ImmutablePerson;
import com.baledung.modifystream.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ModifyStreamUnitTest {
    Logger logger = LoggerFactory.getLogger(ModifyStreamUnitTest.class);
    List<Person> personLst = new ArrayList<Person>();
    List<ImmutablePerson> immutablePersonList = new ArrayList<ImmutablePerson>();

    @BeforeEach
    void prepare() {
        Person person1 = new Person("John", "john@gmail.com");
        Person person2 = new Person("Peter", "peter@gmail.com");
        Person person3 = new Person("Mary", "mary@gmail.com");
        Person person4 = new Person("William", "william@gmail.com");

        personLst.add(person1);
        personLst.add(person2);
        personLst.add(person3);
        personLst.add(person4);
    }

    @BeforeEach
    void prepareImmutablePerson() {
        ImmutablePerson immutablePerson1 = new ImmutablePerson("John", "john@gmail.com");
        ImmutablePerson immutablePerson2 = new ImmutablePerson("Peter", "peter@gmail.com");
        ImmutablePerson immutablePerson3 = new ImmutablePerson("Mary", "mary@gmail.com");
        ImmutablePerson immutablePerson4 = new ImmutablePerson("William", "william@gmail.com");

        immutablePersonList.add(immutablePerson1);
        immutablePersonList.add(immutablePerson2);
        immutablePersonList.add(immutablePerson3);
        immutablePersonList.add(immutablePerson4);
    }

    @Test
    void givenPersonLst_whenRemoveWhileIterating_thenThrowException() {
        assertThrows(NullPointerException.class, () -> {
            personLst.stream().forEach(e -> {
                if(e.getName().equals("John")) {
                    personLst.remove(e);
                }
            });
        });
    }

    @Test
    void givenPersonLst_whenRemoveWhileIteratingWithForEach_thenThrowException() {
        assertThrows(ConcurrentModificationException.class, () -> {
            personLst.forEach(e -> {
                if(e.getName().equals("John")) {
                    personLst.remove(e);
                }
            });
        });
    }

    @Test
    void givenPersonLst_whenRemoveWhileIterating_thenPersonRemoved() {
        assertEquals(4, personLst.size());

        CopyOnWriteArrayList<Person> cps = new CopyOnWriteArrayList<>(personLst);
        cps.stream().forEach(e -> {
            if(e.getName().equals("John")) {
                cps.remove(e);
            }
        });

        assertEquals(3, cps.size());
    }


    @Test
    void givenPersonLst_whenRemovePersonWithFilter_thenPersonRemoved() {
        assertEquals(4, personLst.size());

        List<Person> newPersonLst = personLst.stream()
          .filter(e -> !e.getName().equals("John"))
          .collect(Collectors.toList());

        assertEquals(3, newPersonLst.size());
    }

    @Test
    void givenPersonLst_whenRemovePersonWithRemoveIf_thenPersonRemoved() {
        assertEquals(4, personLst.size());

        personLst.removeIf(e -> e.getName().equals("John"));

        assertEquals(3, personLst.size());
    }

    @Test
    void givenPersonLst_whenUpdatePersonEmailByInterferingWithForEach_thenPersonEmailUpdated() {
        personLst.stream().forEach(e -> e.setEmail(e.getEmail().toUpperCase()));

        personLst.forEach(e -> assertEquals(e.getEmail(), e.getEmail().toUpperCase()));
    }

    @Test
    void givenPersonLst_whenUpdatePersonEmailWithMapMethod_thenPersonEmailUpdated() {
        List<Person> newPersonLst = personLst.stream()
          .map(e -> new Person(e.getName(), e.getEmail().toUpperCase()))
          .collect(Collectors.toList());

        newPersonLst.forEach(e -> assertEquals(e.getEmail(), e.getEmail().toUpperCase()));
    }

    @Test
    void givenPersonLst_whenUpdateImmutablePersonEmailWithMapMethod_thenPersonEmailUpdated() {
        List<ImmutablePerson> newImmutablePersonLst = immutablePersonList.stream()
          .map(e -> e.withEmail(e.getEmail().toUpperCase()))
          .collect(Collectors.toList());

        newImmutablePersonLst.forEach(e -> assertEquals(e.getEmail(), e.getEmail().toUpperCase()));
    }
    @Test
    void givenPersonLst_whenUpdatePersonEmailByInterferingWithPeek_thenPersonEmailUpdated() {
        personLst.stream()
                .peek(e -> e.setEmail(e.getEmail().toUpperCase()))
                .collect(Collectors.toList());

        personLst.forEach(e -> assertEquals(e.getEmail(), e.getEmail().toUpperCase()));
    }

}

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
    List<Person> personList = new ArrayList<Person>();
    List<ImmutablePerson> immutablePersonList = new ArrayList<ImmutablePerson>();

    @BeforeEach
    void prepare() {
        Person person1 = new Person("John", "john@gmail.com");
        Person person2 = new Person("Peter", "peter@gmail.com");
        Person person3 = new Person("Mary", "mary@gmail.com");
        Person person4 = new Person("William", "william@gmail.com");

        personList.add(person1);
        personList.add(person2);
        personList.add(person3);
        personList.add(person4);
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
    void givenPersonList_whenRemoveWhileIterating_thenThrowException() {
        assertThrows(NullPointerException.class, () -> {
            personList.stream().forEach(e -> {
                if(e.getName().equals("John")) {
                    personList.remove(e);
                }
            });
        });
    }

    @Test
    void givenPersonList_whenRemoveWhileIteratingWithForEach_thenThrowException() {
        assertThrows(ConcurrentModificationException.class, () -> {
            personList.forEach(e -> {
                if(e.getName().equals("John")) {
                    personList.remove(e);
                }
            });
        });
    }

    @Test
    void givenPersonList_whenRemoveWhileIterating_thenPersonRemoved() {
        assertEquals(4, personList.size());

        CopyOnWriteArrayList<Person> cps = new CopyOnWriteArrayList<>(personList);
        cps.stream().forEach(e -> {
            if(e.getName().equals("John")) {
                cps.remove(e);
            }
        });

        assertEquals(3, cps.size());
    }


    @Test
    void givenPersonList_whenRemovePersonWithFilter_thenPersonRemoved() {
        assertEquals(4, personList.size());

        List<Person> newPersonList = personList.stream()
          .filter(e -> !e.getName().equals("John"))
          .collect(Collectors.toList());

        assertEquals(3, newPersonList.size());
    }

    @Test
    void givenPersonList_whenUpdatePersonEmailByInterferingWithForEach_thenPersonEmailUpdated() {
        personList.stream().forEach(e -> e.setEmail(e.getEmail().toUpperCase()));

        personList.forEach(e -> assertEquals(e.getEmail(), e.getEmail().toUpperCase()));
    }

    @Test
    void givenPersonList_whenUpdatePersonEmailWithMapMethod_thenPersonEmailUpdated() {
        List<Person> newPersonList = personList.stream()
          .map(e -> new Person(e.getName(), e.getEmail().toUpperCase()))
          .collect(Collectors.toList());

        newPersonList.forEach(e -> assertEquals(e.getEmail(), e.getEmail().toUpperCase()));
    }

    @Test
    void givenPersonList_whenUpdateImmutablePersonEmailWithMapMethod_thenPersonEmailUpdated() {
        List<ImmutablePerson> newImmutablePersonList = immutablePersonList.stream()
          .map(e -> e.withEmail(e.getEmail().toUpperCase()))
          .collect(Collectors.toList());

        newImmutablePersonList.forEach(e -> assertEquals(e.getEmail(), e.getEmail().toUpperCase()));
    }

    @Test
    void givenPersonList_whenUpdatePersonEmailByInterferingWithPeek_thenPersonEmailUpdated() {
        personList.stream()
          .peek(e -> e.setEmail(e.getEmail().toUpperCase()))
          .collect(Collectors.toList());

        personList.forEach(e -> assertEquals(e.getEmail(), e.getEmail().toUpperCase()));
    }

}

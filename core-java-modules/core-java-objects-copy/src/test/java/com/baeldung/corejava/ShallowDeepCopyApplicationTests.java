package com.baeldung.corejava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.corejava.vo.Person;

@SpringBootTest
class ShallowDeepCopyApplicationTests {

    @Test
    void whenAssigningObject_thenShallowCopy() {
        Person person = new Person("Test", "India");
        Person copyPerson = person;
        copyPerson.setName("TestCopy");

        Assertions.assertEquals(person.getName(), copyPerson.getName());
    }

    @Test
    void whenCreatingNewObjectByCopyingValues_thenDeepCopy() {
        Person person = new Person("Test", "India");
        Person newPerson = new Person(person.getName(), person.getAddress());
        newPerson.setName("TestCopy");

        Assertions.assertNotEquals(person.getName(), newPerson.getName());
    }

}

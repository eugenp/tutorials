package com.baeldung.corejava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.corejava.vo.Address;
import com.baeldung.corejava.vo.Person;

@SpringBootTest
class ShallowDeepCopyApplicationTests {

    @Test
    void whenCloningObject_thenShallowCopy() throws CloneNotSupportedException {
        Address address = new Address(321321, "India");
        Person originalPerson = new Person(1, "John", address);
        Person shallowCopyPerson = originalPerson.clone();
        shallowCopyPerson.getAddress().setStreetName("Delhi");

        Assertions.assertEquals(originalPerson.getAddress().getStreetName(),
            shallowCopyPerson.getAddress().getStreetName());
    }

    @Test
    void whenCreatingNewObjectByCopyingValues_thenDeepCopy() {
        Address address = new Address(321321, "India");
        Person originalPerson = new Person(1, "John", address);
        Person deepCopyPerson = new Person(originalPerson.getPersonId(), originalPerson.getName(),
            new Address(originalPerson.getAddress().getPinCode(), originalPerson.getAddress().getStreetName()));
        deepCopyPerson.getAddress().setStreetName("Delhi");

        Assertions.assertNotEquals(originalPerson.getAddress().getStreetName(),
            deepCopyPerson.getAddress().getStreetName());
    }

}

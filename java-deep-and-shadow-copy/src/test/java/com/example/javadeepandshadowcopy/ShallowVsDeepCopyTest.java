package com.example.javadeepandshadowcopy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ShallowVsDeepCopyTest {

    @Test
    public void testShallowCopy() throws CloneNotSupportedException {

        Address address = new Address("Iran", "Tehran");
        Person person = new Person("John", "Doe", address);
        person.getAddress().setCity("Shiraz");
        assertEquals("Shiraz", person.getAddress().getCity());

    }

    @Test
    public void testDeepCopy() {
        Address address = new Address("Iran", "Tehran");
        Person person = new Person("John", "Doe", address);
        Person deepCopy = new Person(person);
        deepCopy.getAddress().setCity("Shiraz");
        assertEquals("Tehran", person.getAddress().getCity());

    }
}

package com.baeldung.java_shallow_deep_copy.unit;

import com.baeldung.java_shallow_deep_copy.data.PersonShallow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class ShallowCopyTest {

    @Test
    void whenTheParameterIsAnImmutableObject_thenShouldNotChangeTheValue (){
        String name = "Hello";
        String surname = "World";
        PersonShallow personShallow = new PersonShallow(name,surname,null);
        surname = "Pluto";
        Assertions.assertNotEquals(surname, personShallow.getSurname());
    }

    @Test
    void whenTheParameterIsNotAnImmutableObject_thenShouldDoAShallowCopy (){
        String name = "Hello";
        String surname = "World";
        List<String> addresses = new ArrayList<>();
        addresses.add("Standford street");
        PersonShallow personShallow = new PersonShallow(name,surname,addresses);
        personShallow.getAddresses().forEach(System.out :: println);
        Assertions.assertEquals(addresses, personShallow.getAddresses());
        addresses.add("Oxford street");
        personShallow.getAddresses().forEach(System.out :: println);
        Assertions.assertEquals(addresses, personShallow.getAddresses());
    }



}

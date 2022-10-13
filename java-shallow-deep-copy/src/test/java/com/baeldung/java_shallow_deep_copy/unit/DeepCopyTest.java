package com.baeldung.java_shallow_deep_copy.unit;

import com.baeldung.java_shallow_deep_copy.data.PersonDeep;
import com.baeldung.java_shallow_deep_copy.data.PersonShallow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DeepCopyTest {

    @Test
    void whenTheParameterIsNotAnImmutableObject_thenShouldDoADeepCopy (){
        String name = "Hello";
        String surname = "World";
        List<String> addresses = new ArrayList<>();
        addresses.add("Standford street");
        PersonDeep personShallow = new PersonDeep(name,surname,addresses);
        personShallow.getAddresses().forEach(System.out :: println);
        Assertions.assertEquals(addresses, personShallow.getAddresses());
        addresses.add("Oxford street");
        personShallow.getAddresses().forEach(System.out :: println);
        Assertions.assertNotEquals(addresses, personShallow.getAddresses());
    }
}

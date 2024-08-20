package com.baeldung.pojo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShallowCopyTest {
    @Test
    void whenShallowCopy_thenAssertAddressEquals() throws CloneNotSupportedException {
        Address addr = new Address("NY");
        Person p1 = new Person("John", addr);
        Person p2 = (Person) p1.clone();
        p2.address.city = "LA";
        assertEquals("LA", p1.address.city); // Shallow copy
    }
}

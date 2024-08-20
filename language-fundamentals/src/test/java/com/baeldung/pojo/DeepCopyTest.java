package com.baeldung.pojo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeepCopyTest {
    @Test
    void whenDeepCopy_thenAssertAddressDifferent() throws CloneNotSupportedException {
        Address addr = new Address("NY");
        PersonDeepCopy p1 = new PersonDeepCopy("John", addr);
        PersonDeepCopy p2 = (PersonDeepCopy) p1.clone();
        p2.address.city = "LA";
        assertEquals("NY", p1.address.city); // Deep copy
    }
}

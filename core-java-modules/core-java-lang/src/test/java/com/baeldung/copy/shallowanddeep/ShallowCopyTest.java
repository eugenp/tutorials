package com.baeldung.copy.shallowanddeep;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ShallowCopyTest {

    @Test
    public void whenMakingAShallowCopy_thenObjectsShouldBeDifferent() throws CloneNotSupportedException {
        Person patrick = new Person("Patrick", 24);

        Person patrickCopy = (Person) patrick.clone();

        patrick.setName("Peter");
        patrick.setAge(40);

        assertEquals("Patrick", patrickCopy.getName());
        assertEquals(24, patrickCopy.getAge());
    }
}

package com.baeldung.copy.shallowanddeep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

public class ShallowCopyUnitTest {

    @Test
    public void whenMakingAShallowCopy_thenObjectsShouldBeDifferent() throws CloneNotSupportedException {
        Person original = new Person("Patrick", 24);

        Person clone = (Person) original.clone();

        assertNotSame(original, clone);
        assertEquals(original.getName(), clone.getName());
        assertEquals(original.getAge(), clone.getAge());
    }
}

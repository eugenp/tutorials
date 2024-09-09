package com.baeldung.shallowvsdeepcopy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class ShallowCopyUnitTest {


    @Test
    public void whenModifyingOriginalObject_thenCopyShouldChange() {
        Person originalPerson = new Person("John");
        Person copiedPerson = originalPerson;

        copiedPerson.setName("Jane");
   
        assertEquals("Jane", originalPerson.getName());
    }
}

package com.baeldung.objectcopy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShallowCopyPersonUnitTest {

    @Test
    public void givenPerson_whenShallowCopy_thenSharedReference() {
        ShallowCopyPerson person1 = new ShallowCopyPerson(30);
        ShallowCopyPerson person2 = person1;
        person1.age = 25;

        assertEquals(25, person2.age);
    }
}

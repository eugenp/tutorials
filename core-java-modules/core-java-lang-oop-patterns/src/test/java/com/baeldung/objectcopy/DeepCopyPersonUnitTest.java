package com.baeldung.objectcopy;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeepCopyPersonUnitTest {

    @Test
    public void givenPerson_whenDeepCopy_thenIndependentCopy() throws IOException, ClassNotFoundException {
        DeepCopyPerson person1 = new DeepCopyPerson(30);
        DeepCopyPerson person2 = DeepCopyPerson.deepCopy(person1);
        person1.age = 25;

        assertEquals(30, person2.age);
    }
}

package com.baeldung.deepshallowcopy;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

class PersonCopyUnitTest {

    @Test
    void givenPerson_ThenDeepCopyOnlyReplacesAllObjectsWithCopies() throws Exception {
        Person original = new Person("John", Calendar.getInstance());

        Person clone = (Person) DeepCloner.deepCopy(original);

        assertEquals(original, clone);
        assertNotSame(original, clone);
        assertNotSame(original.name, clone.name);
        assertNotSame(original.birthdate, clone.birthdate);
    }

    @Test
    void givenPerson_ThenShallowCopyReplacesOnlyTheRootObject() {
        Person original = new Person("John", Calendar.getInstance());

        Person clone = original.clone();

        assertEquals(original, clone);
        assertNotSame(original, clone);
        assertSame(original.name, clone.name);
        assertSame(original.birthdate, clone.birthdate);
    }
}

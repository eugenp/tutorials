package com.baeldung.deepshallowcopy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

public class ShallowDeepCopyExampleUnitTest {

    //Test Shallow Copy
    @Test
    public void whenShallowCopy_thenOriginalChanges() {
        Person originalPerson = new Person("Sundar", 30);
        Person copiedPerson = originalPerson;

        // Modify copiedPerson's name
        copiedPerson.name = "Bob";

        // This will also modify the original person's name
        assertEquals("Bob", originalPerson.name, "Original person's name should be 'Bob' after shallow copy and modification.");
    }

    //Test Deep Copy
    @Test
    public void whenDeepCopy_thenOriginalRemainsUnchanged() {
        Person originalPerson = new Person("Sundar", 30);
        Person deepCopiedPerson = originalPerson.deepCopy();

        // Modify deepCopiedPerson's name
        deepCopiedPerson.name = "Charlie";

        // This will NOT modify the original person's name
        assertEquals("Sundar", originalPerson.name, "Original person's name should remain 'Sundar' after deep copy and modification.");
        assertNotSame(originalPerson, deepCopiedPerson, "Deep copied person should not be the same instance as the original person.");
    }
}

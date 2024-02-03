package com.baeldung.deepshallowcopy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CopyExampleTest {

    @Test
    public void shallowCopy_ModificationsAffectOriginalObject() {
        Person john = new Person("John", 25);
        Person shallowCopy = john;

        shallowCopy.setName("Shallow");

        Assertions.assertEquals("Shallow", john.getName());
    }

    @Test
    public void deepCopy_ModificationsDoNotAffectOriginalObject() {
        Person john = new Person("John", 25);
        Person deepCopy = null;
        try {
            deepCopy = (Person) john.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        deepCopy.setName("Deep");

        Assertions.assertNotEquals("Deep", john.getName());
    }
}
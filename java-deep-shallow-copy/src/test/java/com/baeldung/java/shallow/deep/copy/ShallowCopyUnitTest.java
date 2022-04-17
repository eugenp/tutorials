package com.baeldung.java.shallow.deep.copy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShallowCopyUnitTest {

    @Test
    public void whenCreatingShallowCopy_ReferenceIsSame() throws CloneNotSupportedException {
        PersonShallowCopy p1 = new PersonShallowCopy();
        p1.setName(new StringBuilder("Michael Jordan"));
        p1.setAge(25);

        PersonShallowCopy p2 = (PersonShallowCopy) p1.clone();

        assertTrue(p1.getName() == p2.getName());
        assertEquals(p1.getAge(), p2.getAge());
        p1.getName()
            .append(" Champion");
        assertEquals(p1.getName()
            .toString(), p2.getName()
            .toString());
    }
}

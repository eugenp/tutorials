package com.baeldung.java.shallow.deep.copy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeepCopyUnitTest {

    @Test
    public void whenCreatingDeepCopy_ReferenceIsDifferent() throws CloneNotSupportedException {
        PersonDeepCopy p1 = new PersonDeepCopy();
        p1.setName(new StringBuilder("Michael Jordan"));
        p1.setAge(25);

        PersonDeepCopy p2 = (PersonDeepCopy) p1.clone();

        assertFalse(p1.getName() == p2.getName());
        assertEquals(p1.getName()
            .toString(), p2.getName()
            .toString());
        assertEquals(p1.getAge(), p2.getAge());
        p1.getName()
            .append(" Champion");
        assertNotEquals(p1.getName()
            .toString(), p2.getName()
            .toString());
    }
}

package com.baeldung.javaclass;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class ForNameAndInstanceTest {
    ClassForNameAndInstance tester= new ClassForNameAndInstance();
    @Test
    public void givenString_whenValidClass_shouldReturnClass() throws ClassNotFoundException {
        Class c= tester.createClassForName("java.lang.String");
        assertEquals(c.getName(),"java.lang.String");
    }
    @Test
    public void givenString_whenValidClass_shouldReturnClassObject() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Object o= tester.createNewInstance("java.lang.String");
        assertEquals(o.getClass().getName(),"java.lang.String");
    }
}

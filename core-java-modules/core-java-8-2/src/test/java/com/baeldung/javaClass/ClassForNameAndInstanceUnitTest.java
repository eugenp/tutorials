package com.baeldung.javaClass;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ClassForNameAndInstanceUnitTest {
    ClassForNameAndInstance tester= new ClassForNameAndInstance();
    @Test
    public void givenString_whenValidClass_thenReturnClass() throws ClassNotFoundException {
        Class c= tester.createClassForName("java.lang.String");
        assertEquals(c.getName(),"java.lang.String");
    }
    @Test
    public void givenString_whenValidClass_thenReturnClassObject() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Object o= tester.createNewInstance("java.lang.String");
        assertEquals(o.getClass().getName(),"java.lang.String");
    }
}
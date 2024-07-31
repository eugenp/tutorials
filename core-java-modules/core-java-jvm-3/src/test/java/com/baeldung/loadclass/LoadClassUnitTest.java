package com.baeldung.loadclass;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;


public class LoadClassUnitTest {

    @Test
    public void whenUseClassForName_createdInstanceOfClassClass() throws ClassNotFoundException {
        Class instance = Class.forName("com.baeldung.loadclass.MyClassForLoad");
        assertInstanceOf(Class.class, instance, "instance should be of Class.class");
    }

    @Test
    public void whenUseClassForNameWithNewInstance_createdInstanceOfTargetClas() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Object instance = Class.forName("com.baeldung.loadclass.MyClassForLoad").newInstance();
        assertInstanceOf(MyClassForLoad.class, instance, "instance should be of MyClassForLoad class");
    }

    @Test
    public void whenUseClassForNameWithDeclaredConstructor_newInstanceCreatedInstanceOfTargetClas() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object instance = Class.forName("com.baeldung.loadclass.MyClassForLoad").getDeclaredConstructor().newInstance();
        assertInstanceOf(MyClassForLoad.class, instance, "instance should be of MyClassForLoad class");
    }

}

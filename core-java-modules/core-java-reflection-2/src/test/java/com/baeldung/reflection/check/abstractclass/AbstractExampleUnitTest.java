package com.baeldung.reflection.check.abstractclass;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Date;

class AbstractExampleUnitTest {

    @Test
    void givenAbstractClass_whenCheckModifierIsAbstract_thenTrue() {
        Class<AbstractExample> clazz = AbstractExample.class;
        Assertions.assertTrue(Modifier.isAbstract(clazz.getModifiers()));
    }

    @Test
    void givenInterface_whenCheckModifierIsAbstract_thenTrue() {
        Class<InterfaceExample> clazz = InterfaceExample.class;
        Assertions.assertTrue(Modifier.isAbstract(clazz.getModifiers()));
    }

    @Test
    void givenAbstractClass_whenCheckIsAbstractClass_thenTrue() {
        Class<AbstractExample> clazz = AbstractExample.class;
        int mod = clazz.getModifiers();
        Assertions.assertTrue(Modifier.isAbstract(mod) && !Modifier.isInterface(mod));
    }

    @Test
    void givenConcreteClass_whenCheckIsAbstractClass_thenFalse() {
        Class<Date> clazz = Date.class;
        int mod = clazz.getModifiers();
        Assertions.assertFalse(Modifier.isAbstract(mod) && !Modifier.isInterface(mod));
    }
}

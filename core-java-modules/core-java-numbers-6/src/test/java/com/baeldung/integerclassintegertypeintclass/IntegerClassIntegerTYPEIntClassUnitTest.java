package com.baeldung.integerclassintegertypeintclass;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class IntegerClassIntegerTYPEIntClassUnitTest {
    @Test
    public void testIntegerClass() {
        Class<Integer> integerClass = Integer.class;
        Assertions.assertEquals("java.lang.Integer", integerClass.getName());
        Assertions.assertEquals(Number.class, integerClass.getSuperclass());
        Assertions.assertFalse(integerClass.isPrimitive());
    }

    public void processInteger(Integer value) {
        Assertions.assertEquals("Integer", value.getClass().getSimpleName());
    }

    public void processInt(int value) {
        Assertions.assertEquals("int", Integer.TYPE.getName());
    }

    @Test
    public void testIntegerType() {
        int intValue = 42;
        Integer integerValue = 42;
        processInteger(intValue);
        processInteger(integerValue);
        processInt(intValue);
    }
    @Test
    public void testIntClass() {
        int intValue = 42;
        Class<?> intClass = int.class;
        Assertions.assertEquals("int", intClass.getName());
        Assertions.assertTrue(intClass.isPrimitive());
        Assertions.assertEquals(int.class, intClass);
    }
}

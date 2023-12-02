package com.baeldung.integerclassintegertypeintclass;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class IntegerClassIntegerTYPEIntClassUnitTest {

    @Test
    public void givenIntegerClass_whenGetName_thenVerifyClassName() {
        Class<Integer> integerClass = Integer.class;
        Assertions.assertEquals("java.lang.Integer", integerClass.getName());
        Assertions.assertEquals(Number.class, integerClass.getSuperclass());
        Assertions.assertFalse(integerClass.isPrimitive());
    }

    public int sum(int a, int b) {
        return a + b;
    }

    public int sum(Integer a, Integer b) {
        return a + b;
    }

    public int sum(int a, Integer b) {
        return a + b;
    }

    @Test
    public void givenIntAndInteger_whenAddingValues_thenVerifySum() {
        int primitiveValue = 10;
        Integer wrapperValue = Integer.valueOf(primitiveValue);
        Assertions.assertEquals(20, sum(primitiveValue, primitiveValue));
        Assertions.assertEquals(20, sum(primitiveValue, wrapperValue));
        Assertions.assertEquals(20, sum(wrapperValue, wrapperValue));
        Assertions.assertEquals(Integer.TYPE.getName(), int.class.getName());
    }

    @Test
    public void givenIntValue_whenUsingIntClass_thenVerifyIntClassProperties() {
        Class<?> intClass = int.class;
        Assertions.assertEquals("int", intClass.getName());
        Assertions.assertTrue(intClass.isPrimitive());
        Assertions.assertEquals(int.class, intClass);
    }
}

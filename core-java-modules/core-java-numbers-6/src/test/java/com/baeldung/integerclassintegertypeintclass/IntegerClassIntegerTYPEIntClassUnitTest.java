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
    
    public void processInteger(Integer value) {
        Assertions.assertEquals("Integer", value.getClass().getSimpleName());
    }
    
    public void processInt(int value) {
        Assertions.assertEquals("int", Integer.TYPE.getName());
    }
    
    @Test
    public void givenIntegerType_whenProcessIntegerAndProcessInt_thenVerifyClassNames() {
        int intValue = 42;
        Integer integerValue = 42;
        processInteger(intValue);
        processInteger(integerValue);
        processInt(intValue);
    }
    
    @Test
    public void givenIntValue_whenUsingIntClass_thenVerifyIntClassProperties() {
        int intValue = 42;
        Class<?> intClass = int.class;
        Assertions.assertEquals("int", intClass.getName());
        Assertions.assertTrue(intClass.isPrimitive());
        Assertions.assertEquals(int.class, intClass);
    }
    
}

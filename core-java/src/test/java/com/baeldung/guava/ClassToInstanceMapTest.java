package com.baeldung.guava;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;

public class ClassToInstanceMapTest {
    
    @Test
    public void givenTypes_thenMapInstances(){
        ClassToInstanceMap<Number> numberSystemMap = MutableClassToInstanceMap.create();
        
        byte byteValue = 127;
        
        numberSystemMap.put(Integer.class, 1_000);
        numberSystemMap.put(Double.class, 24.50);
        numberSystemMap.put(Long.class, 2_500L);
        numberSystemMap.put(Byte.class, byteValue);
        
        assertTrue(numberSystemMap.containsValue(1_000));
        assertTrue(numberSystemMap.containsValue(24.50));
        assertTrue(numberSystemMap.containsValue(2_500L));
        assertTrue(numberSystemMap.containsValue(Byte.MAX_VALUE));
    }
}

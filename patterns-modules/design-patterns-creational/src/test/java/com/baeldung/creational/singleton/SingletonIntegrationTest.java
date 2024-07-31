package com.baeldung.creational.singleton;

import org.junit.Test;
import static org.junit.Assert.*;

public class SingletonIntegrationTest {

    @Test
    /**
     * Although there is absolutely no way to determine whether
     * a class is Singleton, in this test case, we will just 
     * check for two objects if they point to same instance or 
     * not. We will also check for their hashcode.
     */
    public void whenGettingMultipleObjects_thenAllPointToSame() {
        //first object
        Singleton obj1 = Singleton.getInstance();
        
        //Second object
        Singleton obj2 = Singleton.getInstance();
        
        assertTrue(obj1 == obj2);
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

}

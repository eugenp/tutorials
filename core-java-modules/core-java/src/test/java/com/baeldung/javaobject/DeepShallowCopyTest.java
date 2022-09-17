package com.baeldung.javaobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class DeepShallowCopyTest {
    @Test
    public void deepCopyTest(){
        ObjectExample obj1 = new ObjectExample();
        ObjectExample deepObj= new ObjectExample();
        obj1.name="Advanced Java";
        deepObj.name="Core Java";

        assertNotEquals(obj1.name,deepObj.name);
        
    }
    @Test
    public void shallowCopyTest(){
        ObjectExample obj1 = new ObjectExample();
        ObjectExample shallowObj= obj1;
        obj1.name="Advanced Java";
        shallowObj.name="Core Java";
        assertEquals(obj1.name,shallowObj.name);

    }

}

package com.baeldung.java.deepcopy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DeepCopyUnitTest {

    @Test
    public void whenCreatingDeepCopy_thenValuesShouldNotBeSame() {
            
            Sample s1 = new Sample();
            Sample s2 = new Sample();
            
            s2.a = 20;
            
            assertNotSame(s1.a,s2.a);
            
    }
}

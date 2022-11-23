package com.baeldung.java.shallowcopy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ShallowCopyUnitTest {

    @Test
    public void whenCreatingShallowCopy_thenValuesShouldBeSame() {
            
            Sample s1 = new Sample();
            Sample s2 = s1;
            
            s2.a = 20;
            
            assertSame(s1.a, s2.a);
            
    }

}

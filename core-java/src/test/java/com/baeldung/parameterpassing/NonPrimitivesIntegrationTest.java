package com.baeldung.parameterpassing;

import org.junit.Assert;
import org.junit.Test;

public class NonPrimitivesIntegrationTest {
    @Test
    public void whenModifyingObjects_thenOriginalObjectChanged() {
        FooClass a = new FooClass(1);
        FooClass b = new FooClass(1);

        // Before Modification
        Assert.assertEquals(a.num, 1);
        Assert.assertEquals(b.num, 1);
        
        NonPrimitives.modify(a, b);
        
        Assert.assertEquals(a.num, 2);
        Assert.assertEquals(b.num, 1);
    }
}

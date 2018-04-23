package com.baeldung.parameterpassing;

import org.junit.Assert;
import org.junit.Test;

public class NonPrimitivesUnitTest {
    @Test
    public void whenModifyingObjects_thenOriginalObjectChanged() {
        Foo a = new Foo(1);
        Foo b = new Foo(1);

        // Before Modification
        Assert.assertEquals(a.num, 1);
        Assert.assertEquals(b.num, 1);
        
        modify(a, b);
        
        // After Modification
        Assert.assertEquals(a.num, 2);
        Assert.assertEquals(b.num, 1);
    }
 
    public static void modify(Foo a1, Foo b1) {
        a1.num++;
       
        b1 = new Foo(1);
        b1.num++;
    }
}
 
class Foo {
    public int num;
   
    public Foo(int num) {
        this.num = num;
    }
}
package com.baeldung.nestedclass;

import org.junit.Test;

public class InnerClassTest {

    @Test
    public void givenInnerClassWhenInstantiating_thenCorrect() {
        Outer outer = new Outer();
        Outer.Inner inner = outer.new Inner();
        inner.test();
    }
}

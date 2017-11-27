package com.baeldung.nestedclass;

import org.junit.Test;

public class NestedClassTest {

    @Test
    public void whenInstantiatingStaticNestedClass_thenCorrect() {
        Enclosing.Nested nested = new Enclosing.Nested();
        nested.test();
    }
}

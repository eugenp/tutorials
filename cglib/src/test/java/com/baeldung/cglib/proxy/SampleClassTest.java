package com.baeldung.cglib.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SampleClassTest {
    @Test
    public void testFixedValue() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleClass.class);
        enhancer.setCallback((FixedValue) () -> "Hello cglib!");
        SampleClass proxy = (SampleClass) enhancer.create();
        assertEquals("Hello cglib!", proxy.test(null));

    }

}
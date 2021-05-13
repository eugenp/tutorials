package com.baeldung.staticClassSingleton;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class StaticClassExampleTest {

    @Test
    public void testStaticClass(){
        StaticClassExample.innerStaticClass innerStaticClass = new StaticClassExample.innerStaticClass("hello");
        Assert.assertEquals("hello", innerStaticClass.getGreeting());
    }
}

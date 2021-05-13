package com.baeldung.staticClassSingleton;

import org.junit.Assert;
import org.junit.Test;

public class SingletonExampleTest {
    @Test
    public void singletonTest(){
        SingletonExample singletonExample = SingletonExample.getInstance();
        Assert.assertEquals(1, singletonExample.counter(1));
        Assert.assertEquals(4, singletonExample.counter(3));
    }
}

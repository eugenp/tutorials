package com.baeldung.ejb.tutorial;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class HelloStatefulWorldTest {

    private HelloStatefulWorldBean statefulBean;
    
    @Before
    public void setup() {
        statefulBean = new HelloStatefulWorldBean();
    }
    
    @Test
    public void statefulTest() {
        String helloWorld = statefulBean.getHelloWorld();
        assertEquals(helloWorld, "Hello Stateful World");
        assertEquals(1, statefulBean.howManyTimes());
        statefulBean.getHelloWorld();
        assertEquals(2, statefulBean.howManyTimes());
    }
    
}

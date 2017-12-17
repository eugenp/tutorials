package com.baeldung.ejb.tutorial;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class HelloStatelessWorldTest {

    private HelloStatelessWorldBean statelessBean;
    
    @Before
    public void setup() {
        statelessBean = new HelloStatelessWorldBean();
    }
    
    @Test
    public void statelessTest() {
        String helloWorld = statelessBean.getHelloWorld();
        assertEquals(helloWorld, "Hello Stateless World!");
    }
    
}

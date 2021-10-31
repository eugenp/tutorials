package com.baeldung.junit5;

import org.junit.jupiter.api.Test;

public class A_UnitTest {

    @Test
    public void first() throws Exception{
        System.out.println("Test A first() start => " + Thread.currentThread().getName());
        Thread.sleep(500);
        System.out.println("Test A first() end => " + Thread.currentThread().getName());
    }

    @Test
    public void second() throws Exception{
        System.out.println("Test A second() start => " + Thread.currentThread().getName());
        Thread.sleep(500);
        System.out.println("Test A second() end => " + Thread.currentThread().getName());
    }

}

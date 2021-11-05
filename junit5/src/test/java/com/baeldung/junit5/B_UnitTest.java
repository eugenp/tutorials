package com.baeldung.junit5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

public class B_UnitTest {

    @Test
    public void first() throws Exception{
        System.out.println("Test B first() start => " + Thread.currentThread().getName());
        Thread.sleep(500);
        System.out.println("Test B first() end => " + Thread.currentThread().getName());
    }

    @Test
    public void second() throws Exception{
        System.out.println("Test B second() start => " + Thread.currentThread().getName());
        Thread.sleep(500);
        System.out.println("Test B second() end => " + Thread.currentThread().getName());
    }

}

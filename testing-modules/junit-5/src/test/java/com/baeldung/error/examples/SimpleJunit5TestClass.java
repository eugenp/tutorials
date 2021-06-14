package com.baeldung.error.examples;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleJunit5TestClass {

    @Test
    public void testCalcOne()
    {
        System.out.println("======TEST ONE EXECUTED=======");
        assertEquals( 4 , (2 + 2));
    }
}


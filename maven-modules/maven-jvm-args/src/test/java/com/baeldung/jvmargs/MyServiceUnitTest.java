package com.baeldung.jvmargs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MyServiceUnitTest {

    MyService myService = new MyService();
    
    @Test
    void whenGetLength_thenZeroIsReturned() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        assertEquals(0, myService.getLength());
    }

}
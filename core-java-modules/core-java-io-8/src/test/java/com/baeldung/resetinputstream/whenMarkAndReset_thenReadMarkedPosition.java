package com.baeldung.resetinputstream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;

class whenMarkAndReset_thenReadMarkedPosition {

    final int readLimit = 500;
    final int EXPECTED_NUMBER = 3;

    @Test
    void test() {
        byte[] buffer = { 1, 2, 3, 4, 5 };
        ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
        int number = bis.read();    //get 1
        number = bis.read();        //get 2
        bis.mark(readLimit);
        number = bis.read();        //get 3
        number = bis.read();        //get 4

        bis.reset();

        number = bis.read();        //should get 3
        assertEquals(number, EXPECTED_NUMBER);
    }
}

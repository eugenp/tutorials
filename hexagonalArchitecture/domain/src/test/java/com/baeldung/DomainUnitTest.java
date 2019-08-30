package com.baeldung;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


class DomainUnitTest {

    @Test
    void testGreeting() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);
        new Domain().greeting();
        String s = byteArrayOutputStream.toString();
        Assertions.assertEquals(s, "Hello World\n");
    }
}

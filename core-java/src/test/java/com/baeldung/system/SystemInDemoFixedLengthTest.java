package com.baeldung.system;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class SystemInDemoFixedLengthTest {

    @Test
    public void givenSystemin_whenCallRead_thenReadInputinResult() throws IOException {
        final int length = 10;
        byte[] name = new byte[length];

        System.out.print("Please enter your name (upto " + length + " characters): ");

        // Commenting it out, since it will always block for user input here when the unit tests run in Jenkins
//        System.in.read(name, 0, length);

        System.out.println("Hello " + new String(name));

        Assert.assertNotNull(name);
    }
}

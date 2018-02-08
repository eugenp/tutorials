package com.baeldung.system;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemInDemoVariableLengthTest {

    @Test
    public void givenSystemin_whenCallReadLine_thenReadInputinResult() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String name = "";

        System.out.print("Enter your name: ");

        // Commenting it out, since it will always block for user input here when the unit tests run in Jenkins
//        name = reader.readLine();

        System.out.println("Hello " + name);
        reader.close();
    }
}

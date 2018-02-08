package com.baeldung.system;

import org.junit.Assert;
import org.junit.Test;

import java.io.Console;

public class SystemConsoleTest {

    @Test
    public void givenConsole_whenReadLineAndPassword_thenValidInputinResult() {
        Console console = System.console();
        String name = null;
        char[] password = null;

        Assert.assertNull(name);
        Assert.assertNull(password);

        if (console != null) {
            name = console.readLine("%s", "Enter your name: ");
            password = console.readPassword("%s", "Password: ");

            console.printf("Hello %s", name);

            Assert.assertNotNull(name);
            Assert.assertNotNull(password);
        } else {
            System.out.println("Console not available.");
            Assert.assertNull(console);
        }
    }
}

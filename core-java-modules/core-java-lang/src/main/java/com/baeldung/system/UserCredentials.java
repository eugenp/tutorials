package com.baeldung.system;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Note: This class is not meant for unit-testing since it uses system
 * features at low level and that it uses 'System' standard input stream
 * methods to read text from user. Also unit-tests in CI environments
 * don't have console input for user to type in text. But the usage below
 * demonstrates how the methods can be used.
 */
public class UserCredentials {

    public String readUsername(int length) throws IOException {
        byte[] name = new byte[length];
        System.in.read(name, 0, length); // by default, from the console
        return new String(name);
    }

    public String readUsername() throws IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    public String readUsernameWithPrompt() {
        Console console = System.console();

        return console == null ? null : // Console not available
                console.readLine("%s", "Enter your name: ");
    }
}

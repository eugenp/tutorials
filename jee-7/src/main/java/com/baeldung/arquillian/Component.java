package com.baeldung.arquillian;

import java.io.PrintStream;

public class Component {
    public void sendMessage(PrintStream to, String msg) {
        to.println(message(msg));
    }

    public String message(String msg) {
        return "Message, " + msg;
    }
}
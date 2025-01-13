package com.baeldung.jline;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class SystemTerminalExample {
    public static void main(String[] args) throws Exception {
        try (Terminal terminal = TerminalBuilder.builder().system(true).build()) {
            terminal.writer().println("Size: " + terminal.getSize());
            terminal.flush();
        }
    }
}


package com.baeldung.lanterna;

import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.junit.jupiter.api.Test;

public class TerminalLiveTest {
    @Test
    void whenCreatingADefaultTerminal_thenATerminalIsCreated() throws Exception {
        try (Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            terminal.enterPrivateMode();
            Thread.sleep(5000);
            terminal.exitPrivateMode();
        }
    }
}

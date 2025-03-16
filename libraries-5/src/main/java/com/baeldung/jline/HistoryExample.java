package com.baeldung.jline;

import org.jline.reader.*;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;

public class HistoryExample {
    public static void main(String[] args) throws Exception {
        try (Terminal terminal = TerminalBuilder.builder().system(true).build()) {
            LineReader lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .option(LineReader.Option.HISTORY_IGNORE_DUPS, false)
                .variable(LineReader.HISTORY_FILE, Path.of("target/jline-history"))
                .variable(LineReader.HISTORY_SIZE, 5)
                .build();
            while (true) {
                try {
                    String line = lineReader.readLine("> ");
                    terminal.writer().println("Read: " + line);
                } catch (UserInterruptException e) {
                    // Ignore
                } catch (EndOfFileException e) {
                    return;
                }
            }
        }
    }
}

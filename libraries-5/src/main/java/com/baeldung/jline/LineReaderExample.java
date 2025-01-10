package com.baeldung.jline;

import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class LineReaderExample {
    public static void main(String[] args) throws Exception {
        try (Terminal terminal = TerminalBuilder.builder().system(true).build()) {
            LineReader lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build();
            while (true) {
                try {
                    String line = lineReader.readLine("> ");
                    terminal.writer().println("Read: " + line);
                    if (line.equals("quit")) {
                        return;
                    }
                } catch (UserInterruptException e) {
                    terminal.writer().println("^C");
                    terminal.writer().println("Interrupted");
                } catch (EndOfFileException e) {
                    terminal.writer().println("^D");
                    terminal.writer().println("Goodbye");
                }
            }
        }
    }
}

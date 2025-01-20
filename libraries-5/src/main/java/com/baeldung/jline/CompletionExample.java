package com.baeldung.jline;

import org.jline.builtins.Completers;
import org.jline.reader.*;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.nio.file.Path;

public class CompletionExample {
    public static void main(String[] args) throws Exception {
        try (Terminal terminal = TerminalBuilder.builder().system(true).build()) {
            Completer completer = new AggregateCompleter(
                new StringsCompleter("foo", "bar", "baz"),
                new Completers.FilesCompleter(Path.of("Target")),
                new Completers.DirectoriesCompleter(Path.of("Target"))
            );

            LineReader lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(completer)
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

package org.baeldung.hexa.cli;

import java.io.IOException;

public interface CliInputParser {

    void processUserInput() throws IOException;

    void consumeBooks();

    void produceBooks();

}
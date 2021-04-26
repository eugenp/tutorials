package com.baeldung.hexagonal;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainFlowUnitTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void whenAskingForBooks_thenLoadCorrectList() {
        ProcessUserInput userInput = new AlwaysTrueProcessUserInputImpl();
        PrintBooksList booksList = new ConsolePrintBookListImpl();
        LoadBooksList loadBooksList = new MockLoadBooksListImpl();

        TopBooksViewerCore core = new TopBooksViewerCore(loadBooksList, booksList, userInput);

        core.showTopBooks();

        String result = outContent.toString();

        assertNotNull(result);

        Map<Object, List<String>> countList = Arrays.stream(result.split("\\s+"))
            .collect(Collectors.groupingBy(s -> s));

        assertEquals(5, countList.get("Book").size());
    }

}

package com.baeldung.outputtofile;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.google.common.collect.Lists;

public class ConsoleOutputToFileUnitTest {

    // @formatter:off
    private final static List<String> OUTPUT_LINES = Lists.newArrayList(
      "I came",
      "I saw",
      "I conquered");
    // @formatter:on

    @Test
    void whenReplacingSystemOutPrintStreamWithFileOutputStream_thenOutputsGoToFile(@TempDir Path tempDir) throws IOException {
        PrintStream originalOut = System.out;
        Path outputFilePath = tempDir.resolve("file-output.txt");
        PrintStream out = new PrintStream(Files.newOutputStream(outputFilePath), true);
        System.setOut(out);

        OUTPUT_LINES.forEach(line -> System.out.println(line));
        assertTrue(outputFilePath.toFile()
          .exists(), "The file exists");
        assertLinesMatch(OUTPUT_LINES, Files.readAllLines(outputFilePath));
        System.setOut(originalOut);
    }

    @Test
    void whenUsingDualPrintStream_thenOutputsGoToConsoleAndFile(@TempDir Path tempDir) throws IOException {
        PrintStream originalOut = System.out;
        Path outputFilePath = tempDir.resolve("dual-output.txt");
        DualPrintStream dualOut = new DualPrintStream(Files.newOutputStream(outputFilePath), System.out);
        System.setOut(dualOut);

        OUTPUT_LINES.forEach(line -> System.out.println(line));
        assertTrue(outputFilePath.toFile()
          .exists(), "The file exists");
        assertLinesMatch(OUTPUT_LINES, Files.readAllLines(outputFilePath));
        System.setOut(originalOut);

    }
}
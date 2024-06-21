package com.baeldung.readlastlines;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.junit.jupiter.api.Test;

public class ReadLastLinesUnitTest {

    private static final String FILE_PATH = "src/test/resources/data.txt";
    private static final int TOTAL_LINES = 10;
    private static final int LAST_LINES_TO_READ = 3;
    private static final String OUTPUT_TO_VERIFY = "line 8\nline 9\nline 10";

    @Test
    public void givenFile_whenUsingBufferedReader_thenExtractedLastLinesCorrect() throws IOException {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(FILE_PATH))) {
            for (int i = 0; i < (TOTAL_LINES - LAST_LINES_TO_READ); i++) {
                br.readLine();
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = (TOTAL_LINES - LAST_LINES_TO_READ); i < TOTAL_LINES; i++) {
                stringBuilder.append(br.readLine()).append("\n");
            }

            assertEquals(stringBuilder.toString().trim(), OUTPUT_TO_VERIFY);
        }
    }

    @Test
    public void givenFile_whenUsingScanner_thenExtractedLastLinesCorrect() throws IOException {
        try (Scanner scanner = new Scanner(new File(FILE_PATH))) {
            for (int i = 0; i < (TOTAL_LINES - LAST_LINES_TO_READ); i++) {
                scanner.nextLine();
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = (TOTAL_LINES - LAST_LINES_TO_READ); i < TOTAL_LINES; i++) {
                stringBuilder.append(scanner.nextLine()).append("\n");
            }

            assertEquals(stringBuilder.toString().trim(), OUTPUT_TO_VERIFY);
        }
    }

    @Test
    public void givenLargeFile_whenUsingFilesAPI_thenExtractedLastLinesCorrect() throws IOException{
        try (Stream<String> lines = Files.lines(Paths.get(FILE_PATH))) {
            Stream<String> remainingLines = lines.skip(TOTAL_LINES - LAST_LINES_TO_READ);

            assertEquals(OUTPUT_TO_VERIFY, remainingLines.collect(Collectors.joining("\n")));
        }
    }

    @Test
    public void givenFile_whenUsingFileUtils_thenExtractedLastLinesCorrect() throws IOException{
        File file = new File(FILE_PATH);
        List<String> lines = FileUtils.readLines(file, "UTF-8");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = (TOTAL_LINES - LAST_LINES_TO_READ); i < TOTAL_LINES; i++) {
            stringBuilder.append(lines.get(i)).append("\n");
        }

        assertEquals(OUTPUT_TO_VERIFY, stringBuilder.toString().trim());
    }

    @Test
    public void givenFile_whenUsingReverseFileReader_thenExtractedLastLinesCorrect() throws IOException{
        File file = new File(FILE_PATH);
        try (ReversedLinesFileReader rlfReader = new ReversedLinesFileReader(file, StandardCharsets.UTF_8)) {
            List<String> lastLines = rlfReader.readLines(LAST_LINES_TO_READ);
            StringBuilder stringBuilder = new StringBuilder();
            Collections.reverse(lastLines);
            lastLines.forEach(
              line -> stringBuilder.append(line).append("\n")
            );

            assertEquals(OUTPUT_TO_VERIFY, stringBuilder.toString().trim());
        }
    }

}

package com.baeldung.linenumber;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class LineAtGivenNumberUnitTest {

    private static final String FILE_PATH = "src/test/resources/linesInput.txt";

    @Test
    public void givenFile_whenUsingBufferedReader_thenExtractedLineIsCorrect() throws IOException {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(FILE_PATH))) {
            for (int i = 0; i < 3; i++) {
                br.readLine();
            }

            String extractedLine = br.readLine();
            assertEquals("Line 4", extractedLine);
        }
    }

    @Test
    public void givenFile_whenUsingScanner_thenExtractedLineIsCorrect() throws IOException {
        try (Scanner scanner = new Scanner(new File(FILE_PATH))) {
            for (int i = 0; i < 3; i++) {
                scanner.nextLine();
            }

            String extractedLine = scanner.nextLine();
            assertEquals("Line 4", extractedLine);
        }
    }

    @Test
    public void givenSmallFile_whenUsingFilesAPI_thenExtractedLineIsCorrect() throws IOException {
        String extractedLine = Files.readAllLines(Paths.get(FILE_PATH)).get(4);

        assertEquals("Line 5", extractedLine);
    }

    @Test
    public void givenLargeFile_whenUsingFilesAPI_thenExtractedLineIsCorrect() throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(FILE_PATH))) {
            String extractedLine = lines.skip(4).findFirst().get();

            assertEquals("Line 5", extractedLine);
        }
    }

    @Test
    public void givenFile_whenUsingFileUtils_thenExtractedLineIsCorrect() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("linesInput.txt").getFile());

        List<String> lines = FileUtils.readLines(file, "UTF-8");

        String extractedLine = lines.get(0);
        assertEquals("Line 1", extractedLine);
    }

    @Test
    public void givenFile_whenUsingIOUtils_thenExtractedLineIsCorrect() throws IOException {
        String fileContent = IOUtils.toString(new FileInputStream(FILE_PATH), StandardCharsets.UTF_8);

        String extractedLine = fileContent.split(System.lineSeparator())[0];
        assertEquals("Line 1", extractedLine);
    }
}

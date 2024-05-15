package com.baeldung.rmlinebreaks;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RemoveLinebreaksUnitTest {

    private Path file1Path() throws Exception {
        return Paths.get(this.getClass().getClassLoader().getResource("multiple-line-1.txt").toURI());
    }

    private Path file2Path() throws Exception {
        return Paths.get(this.getClass().getClassLoader().getResource("multiple-line-2.txt").toURI());
    }

    @Test
    void whenRemovingLineSeparatorFromFile1_thenGetTheExpectedResult() throws Exception {
        String content = Files.readString(file1Path(), StandardCharsets.UTF_8);

        String result = content.replace(System.getProperty("line.separator"), "");
        assertEquals("A, B, C, D, E, F", result);
    }

    @Test
    void whenRemovingLineSeparatorFromFile2_thenNotGetTheExpectedResult() throws Exception {
        String content = Files.readString(file2Path(), StandardCharsets.UTF_8);

        String result = content.replace(System.getProperty("line.separator"), "");
        assertNotEquals("A, B, C, D, E, F", result); // <-- NOT equals assertion!
    }

    @Test
    void whenRemovingAllLinebreaks_thenGetTheExpectedResult() throws Exception {
        String content1 = Files.readString(file1Path(), StandardCharsets.UTF_8);

        // file contains CRLF
        String content2 = Files.readString(file2Path(), StandardCharsets.UTF_8);

        String result1 = content1.replace("\r", "").replace("\n", "");
        String result2 = content2.replace("\r", "").replace("\n", "");

        assertEquals("A, B, C, D, E, F", result1);
        assertEquals("A, B, C, D, E, F", result2);

        String resultReplaceAll = content2.replaceAll("[\\n\\r]", "");
        assertEquals("A, B, C, D, E, F", resultReplaceAll);

    }

    @Test
    void whenUsingReadAllLinesAndJoin_thenGetExpectedResult() throws Exception {
        List<String> lines1 = Files.readAllLines(file1Path(), StandardCharsets.UTF_8);

        // file contains CRLF
        List<String> lines2 = Files.readAllLines(file2Path(), StandardCharsets.UTF_8);

        String result1 = String.join("", lines1);
        String result2 = String.join("", lines2);

        assertEquals("A, B, C, D, E, F", result1);
        assertEquals("A, B, C, D, E, F", result2);
    }

}
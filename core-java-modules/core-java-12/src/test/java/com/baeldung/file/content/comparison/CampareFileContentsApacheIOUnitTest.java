package com.baeldung.file.content.comparison;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CampareFileContentsApacheIOUnitTest {

    public static Path path1 = null;
    public static Path path2 = null;

    @BeforeAll
    public static void setup() throws IOException {

        path1 = Files.createTempFile("file1Test", ".txt");
        path2 = Files.createTempFile("file2Test", ".txt");
    }

    @Test
    public void whenFilesIdentical_thenReturnTrue() throws IOException {

        InputStream inputStream1 = new FileInputStream(path1.toFile());
        InputStream inputStream2 = new FileInputStream(path2.toFile());

        Files.writeString(path1, "testing line 1" + System.lineSeparator() + "line 2");
        Files.writeString(path2, "testing line 1" + System.lineSeparator() + "line 2");

        assertTrue(IOUtils.contentEquals(inputStream1, inputStream2));
    }

    @Test
    public void whenFilesDifferent_thenReturnFalse() throws IOException {

        InputStream inputStream1 = new FileInputStream(path1.toFile());
        InputStream inputStream2 = new FileInputStream(path2.toFile());

        Files.writeString(path1, "testing line " + System.lineSeparator() + "line 2");
        Files.writeString(path2, "testing line 1" + System.lineSeparator() + "line 2");

        assertFalse(IOUtils.contentEquals(inputStream1, inputStream2));
    }

    @Test
    public void whenFilesIdenticalIgnoreEOF_thenReturnTrue() throws IOException {

        Files.writeString(path1, "testing line 1 \n line 2");
        Files.writeString(path2, "testing line 1 \r\n line 2");

        Reader reader1 = new BufferedReader(new FileReader(path1.toFile()));
        Reader reader2 = new BufferedReader(new FileReader(path2.toFile()));

        assertTrue(IOUtils.contentEqualsIgnoreEOL(reader1, reader2));
    }

    @Test
    public void whenFilesNotIdenticalIgnoreEOF_thenReturnFalse() throws IOException {

        Files.writeString(path1, "testing line  \n line 2");
        Files.writeString(path2, "testing line 1 \r\n line 2");

        Reader reader1 = new BufferedReader(new FileReader(path1.toFile()));
        Reader reader2 = new BufferedReader(new FileReader(path2.toFile()));

        assertFalse(IOUtils.contentEqualsIgnoreEOL(reader1, reader2));
    }

    @AfterAll
    public static void shutDown() {

        path1.toFile()
            .deleteOnExit();
        path2.toFile()
            .deleteOnExit();
    }
}

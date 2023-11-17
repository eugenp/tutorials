package com.baeldung.filetofrombytearray;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

class FileToByteArrayUnitTest {

    private static final String FILE_NAME = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "sample.txt";
    private final byte[] expectedByteArray = { 72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100 };

    @Test
    void givenFile_whenUsingFileInputStreamClass_thenConvert() throws IOException {
        File myFile = new File(FILE_NAME);
        byte[] byteArray = new byte[(int) myFile.length()];
        try (FileInputStream inputStream = new FileInputStream(myFile)) {
            inputStream.read(byteArray);
        }

        assertArrayEquals(expectedByteArray, byteArray);
    }

    @Test
    void givenFile_whenUsingNioApiFilesClass_thenConvert() throws IOException {
        byte[] byteArray = Files.readAllBytes(Paths.get(FILE_NAME));

        assertArrayEquals(expectedByteArray, byteArray);
    }

    @Test
    void givenFile_whenUsingApacheCommonsIOUtilsClass_thenConvert() throws IOException {
        File myFile = new File(FILE_NAME);
        byte[] byteArray = new byte[(int) myFile.length()];
        try (FileInputStream inputStream = new FileInputStream(myFile)) {
            byteArray = IOUtils.toByteArray(inputStream);
        }

        assertArrayEquals(expectedByteArray, byteArray);
    }

    @Test
    void givenFile_whenUsingApacheCommonsFileUtilsClass_thenConvert() throws IOException {
        byte[] byteArray = FileUtils.readFileToByteArray(new File(FILE_NAME));

        assertArrayEquals(expectedByteArray, byteArray);
    }

    @Test
    void givenFile_whenUsingGuavaFilesClass_thenConvert() throws IOException {
        byte[] byteArray = com.google.common.io.Files.toByteArray(new File(FILE_NAME));

        assertArrayEquals(expectedByteArray, byteArray);
    }

}

package com.baeldung.extension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

import com.google.common.io.Files;

public class FileExtensionUnitTest {

    private static final String FILE_1 = "MyClass.java";
    private static final String FILE_2 = "fileWithoutExt";
    private static final String FILE_3 = ".gitignore";
    private static final String FILE_4 = "/var/log/app.log";
    private static final String FILE_5 = "";

    private static final String EXPECTED_1 = "java";
    private static final String EXPECTED_2 = "";
    private static final String EXPECTED_3 = "gitignore";
    private static final String EXPECTED_4 = "log";
    private static final String EXPECTED_5 = "";

    public String getFileExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex >= 0) {
            return filename.substring(dotIndex + 1);
        }
        return "";
    }

    @Test
    public void whenUsingStringMethods_thenExtensionIsTrue() {
        assertNull(getFileExtension(null));
        assertEquals(EXPECTED_1, getFileExtension(FILE_1));
        assertEquals(EXPECTED_2, getFileExtension(FILE_2));
        assertEquals(EXPECTED_3, getFileExtension(FILE_3));
        assertEquals(EXPECTED_4, getFileExtension(FILE_4));
        assertEquals(EXPECTED_5, getFileExtension(FILE_5));
    }

    @Test
    public void whenApacheCommonsIO_thenExtensionIsTrue() {
        assertNull(FilenameUtils.getExtension(null));
        assertEquals(EXPECTED_1, FilenameUtils.getExtension(FILE_1));
        assertEquals(EXPECTED_2, FilenameUtils.getExtension(FILE_2));
        assertEquals(EXPECTED_3, FilenameUtils.getExtension(FILE_3));
        assertEquals(EXPECTED_4, FilenameUtils.getExtension(FILE_4));
        assertEquals(EXPECTED_5, FilenameUtils.getExtension(FILE_5));
    }

    @Test
    public void whenUsingGuava_thenExtensionIsTrue() {
        assertEquals(EXPECTED_1, Files.getFileExtension(FILE_1));
        assertEquals(EXPECTED_2, Files.getFileExtension(FILE_2));
        assertEquals(EXPECTED_3, Files.getFileExtension(FILE_3));
        assertEquals(EXPECTED_4, Files.getFileExtension(FILE_4));
        assertEquals(EXPECTED_5, Files.getFileExtension(FILE_5));

        assertThrows(NullPointerException.class, () -> Files.getFileExtension(null));
    }

}
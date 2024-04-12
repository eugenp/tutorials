package com.baeldung.filename;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ExtractFilenameUnitTest {

    private static final String PATH_LINUX = "/root/with space/subDir/myFile.linux";
    private static final String EXPECTED_FILENAME_LINUX = "myFile.linux";

    private static final String PATH_WIN = "C:\\root\\with space\\subDir\\myFile.win";
    private static final String EXPECTED_FILENAME_WIN = "myFile.win";


    @Test
    void givenAbsolutePath_whenExtractingFilenameAsString_thenGetTheFilename() {
        int index = PATH_LINUX.lastIndexOf(File.separator);
        String filenameLinux = PATH_LINUX.substring(index + 1);
        assertEquals(EXPECTED_FILENAME_LINUX, filenameLinux);

        // only works on Windows system:
        //int index = PATH_WIN.lastIndexOf(File.pathSeparator);
        //String filenameWin = PATH_WIN.substring(index + 1);
        //assertEquals(EXPECTED_FILENAME_WIN, filenameWin);
    }


    @Test
    void givenAbsolutePath_whenUsingFileGetName_thenGetTheFilename() {
        File fileLinux = new File(PATH_LINUX);
        assertEquals(EXPECTED_FILENAME_LINUX, fileLinux.getName());

        // only works on Windows system:
        // File fileWin = new File(PATH_WIN);
        // assertEquals(EXPECTED_FILENAME_WIN, fileWin.getName());
    }

    @Test
    void givenAbsolutePath_whenUsingPath_thenGetTheFilename() {
        Path pathLinux = Paths.get(PATH_LINUX);
        assertEquals(EXPECTED_FILENAME_LINUX, pathLinux.getFileName().toString());

        // only works on Windows system:
        // Path pathWin = Paths.get(PATH_WIN);
        // assertEquals(EXPECTED_FILENAME_WIN, pathWin.getFileName().toString());
    }

    @Test
    void givenAbsolutePath_whenUsingFilenameUtils_thenGetTheFilename() {
        String filenameLinux = FilenameUtils.getName(PATH_LINUX);
        assertEquals(EXPECTED_FILENAME_LINUX, filenameLinux);

        String filenameWin = FilenameUtils.getName(PATH_WIN);
        assertEquals(EXPECTED_FILENAME_WIN, filenameWin);

        String filenameToBreak = FilenameUtils.getName("/root/somedir/magic\\file.txt");
        assertNotEquals("magic\\file.txt", filenameToBreak); // <-- filenameToBreak = "file.txt", but we expect: magic\file.txt
    }
}

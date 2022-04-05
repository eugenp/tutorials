package com.baeldung.jimfs;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilePathReaderUnitTest {

    private static final String DIRECTORY_NAME = "baeldung";

    private final FilePathReader filePathReader = new FilePathReader();

    @Test
    @DisplayName("Should get path on windows")
    void givenWindowsSystem_shouldGetPath_thenReturnWindowsPath() throws Exception {
        final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.windows());
        final Path path = getPathToFile(fileSystem);

        final String stringPath = filePathReader.getSystemPath(path);

        assertEquals("C:\\work\\" + DIRECTORY_NAME, stringPath);
    }

    @Test
    @DisplayName("Should get path on unix")
    void givenUnixSystem_shouldGetPath_thenReturnUnixPath() throws Exception {
        final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());
        final Path path = getPathToFile(fileSystem);

        final String stringPath = filePathReader.getSystemPath(path);

        assertEquals("/work/" + DIRECTORY_NAME, stringPath);
    }

    private Path getPathToFile(final FileSystem fileSystem) throws Exception {
        final Path path = fileSystem.getPath(DIRECTORY_NAME);
        Files.createDirectory(path);

        return path;
    }
}
package com.baeldung.jimfs;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileRepositoryUnitTest implements FileTestProvider {

    private final FileRepository fileRepository = new FileRepository();

    @Test
    @DisplayName("Should create a file on a file system")
    void givenUnixSystem_whenCreatingFile_thenCreatedInPath() {
        final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());
        final String fileName = "newFile.txt";
        final Path pathToStore = fileSystem.getPath("");

        fileRepository.create(pathToStore, fileName);

        assertTrue(Files.exists(pathToStore.resolve(fileName)));
    }

    @Test
    @DisplayName("Should read the content of the file")
    void givenOSXSystem_whenReadingFile_thenContentIsReturned() throws Exception {
        final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.osX());
        final Path resourceFilePath = fileSystem.getPath(RESOURCE_FILE_NAME);
        Files.copy(getResourceFilePath(), resourceFilePath);

        final String content = fileRepository.read(resourceFilePath);

        assertEquals(FILE_CONTENT, content);
    }

    @Test
    @DisplayName("Should update the content of the file")
    void givenWindowsSystem_whenUpdatingFile_thenContentHasChanged() throws Exception {
        final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.windows());
        final Path resourceFilePath = fileSystem.getPath(RESOURCE_FILE_NAME);
        Files.copy(getResourceFilePath(), resourceFilePath);
        final String newContent = "I'm updating you.";

        final String content = fileRepository.update(resourceFilePath, newContent);

        assertEquals(newContent, content);
        assertEquals(newContent, fileRepository.read(resourceFilePath));
    }

    @Test
    @DisplayName("Should delete file")
    void givenCurrentSystem_whenDeletingFile_thenFileHasBeenDeleted() throws Exception {
        final FileSystem fileSystem = Jimfs.newFileSystem();
        final Path resourceFilePath = fileSystem.getPath(RESOURCE_FILE_NAME);
        Files.copy(getResourceFilePath(), resourceFilePath);

        fileRepository.delete(resourceFilePath);

        assertFalse(Files.exists(resourceFilePath));
    }
}
package com.baeldung.jimf;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FileRepositoryUnitTest implements FileTestProvider {

    private final FileRepository fileRepository = new FileRepository();

    private static Stream<Arguments> provideFileSystem() {
        return Stream.of(Arguments.of(Jimfs.newFileSystem(Configuration.unix())), Arguments.of(Jimfs.newFileSystem(Configuration.windows())), Arguments.of(Jimfs.newFileSystem(Configuration.osX())));
    }

    @ParameterizedTest
    @DisplayName("Should create a file on a file system")
    @MethodSource("provideFileSystem")
    void shouldCreateFile(final FileSystem fileSystem) throws Exception {
        final String fileName = "newFile.txt";
        final Path pathToStore = fileSystem.getPath("");

        fileRepository.create(pathToStore, fileName);

        assertTrue(Files.exists(pathToStore.resolve(fileName)));
    }

    @ParameterizedTest
    @DisplayName("Should read the content of the file")
    @MethodSource("provideFileSystem")
    void shouldReadFileContent_thenReturnIt(final FileSystem fileSystem) throws Exception {
        final Path resourceFilePath = fileSystem.getPath(RESOURCE_FILE_NAME);
        Files.copy(getResourceFilePath(), resourceFilePath);

        final String content = fileRepository.read(resourceFilePath);

        assertEquals(FILE_CONTENT, content);
    }

    @ParameterizedTest
    @DisplayName("Should update content of the file")
    @MethodSource("provideFileSystem")
    void shouldUpdateContentOfTheFile(final FileSystem fileSystem) throws Exception {
        final Path resourceFilePath = fileSystem.getPath(RESOURCE_FILE_NAME);
        Files.copy(getResourceFilePath(), resourceFilePath);
        final String newContent = "I'm updating you.";

        final String content = fileRepository.update(resourceFilePath, newContent);

        assertEquals(newContent, content);
        assertEquals(newContent, fileRepository.read(resourceFilePath));
    }

    @ParameterizedTest
    @DisplayName("Should update delete file")
    @MethodSource("provideFileSystem")
    void shouldDeleteFile(final FileSystem fileSystem) throws Exception {
        final Path resourceFilePath = fileSystem.getPath(RESOURCE_FILE_NAME);
        Files.copy(getResourceFilePath(), resourceFilePath);

        fileRepository.delete(resourceFilePath);

        assertFalse(Files.exists(resourceFilePath));
    }
}
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
        final String fileName = "test.txt";
        final Path pathToStore = fileSystem.getPath("");

        fileRepository.create(pathToStore, fileName);

        assertTrue(Files.exists(pathToStore.resolve(fileName)));
    }

    @ParameterizedTest
    @DisplayName("Should read the content of the file")
    @MethodSource("provideFileSystem")
    void shouldReadFileContent_thenReturnIt(final FileSystem fileSystem) throws Exception {
        final Path pathToStore = fileSystem.getPath(RESOURCE_FILE_NAME);
        Files.copy(getResourceFilePath(), pathToStore);

        final String content = fileRepository.read(pathToStore);

        assertEquals(FILE_CONTENT, content);
    }

    @ParameterizedTest
    @DisplayName("Should update content of the file")
    @MethodSource("provideFileSystem")
    void shouldUpdateContentOfTheFile(final FileSystem fileSystem) throws Exception {
        final Path pathToStore = fileSystem.getPath(RESOURCE_FILE_NAME);
        Files.copy(getResourceFilePath(), pathToStore);
        final String newContent = "NEW_CONTENT";

        final String content = fileRepository.update(pathToStore, newContent);

        assertEquals(newContent, content);
        assertEquals(newContent, fileRepository.read(pathToStore));
    }

    @ParameterizedTest
    @DisplayName("Should update delete file")
    @MethodSource("provideFileSystem")
    void shouldDeleteFile(final FileSystem fileSystem) throws Exception {
        final Path pathToStore = fileSystem.getPath(RESOURCE_FILE_NAME);
        Files.copy(getResourceFilePath(), pathToStore);

        fileRepository.delete(pathToStore);

        assertFalse(Files.exists(pathToStore));
    }
}
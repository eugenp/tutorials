package com.baeldung.jimfs;

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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileManipulationUnitTest implements FileTestProvider {

    private final FileManipulation fileManipulation = new FileManipulation();

    private static Stream<Arguments> provideFileSystem() {
        return Stream.of(Arguments.of(Jimfs.newFileSystem(Configuration.unix())), Arguments.of(Jimfs.newFileSystem(Configuration.windows())), Arguments.of(Jimfs.newFileSystem(Configuration.osX())));
    }

    @ParameterizedTest
    @DisplayName("Should move file to new destination")
    @MethodSource("provideFileSystem")
    void givenEachSystem_whenMovingFile_thenMovedToNewPath(final FileSystem fileSystem) throws Exception {
        final Path origin = fileSystem.getPath(RESOURCE_FILE_NAME);
        Files.copy(getResourceFilePath(), origin);
        final Path destination = fileSystem.getPath("newDirectory", RESOURCE_FILE_NAME);

        fileManipulation.move(origin, destination);

        assertFalse(Files.exists(origin));
        assertTrue(Files.exists(destination));
    }
}
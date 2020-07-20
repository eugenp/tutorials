package com.baeldung.lob;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FileStorageRepositoryUnitTest {
    final Logger logger = LoggerFactory.getLogger(getClass());
    static final Path NEW_DIRECTORY = ResourceReader.resourcePath();
    static String EXISTING_FILE = "/lob/wise-cat.jpeg";

    @Test
    void givenFileAndDirectory_whenSaveToFileSystem_shallCreateIt() throws IOException {
        long freeMemory = Runtime.getRuntime()
            .freeMemory();
        new FileStorageRepository().saveToFileSystem(ResourceReader.readResourceAsFile(EXISTING_FILE), NEW_DIRECTORY);
        logMemory(freeMemory);
        Assertions.assertThat(Files.exists(Paths.get(NEW_DIRECTORY + EXISTING_FILE)))
            .isTrue();
    }

    private final void logMemory(long freeMemoryBefore) {
        logger.info("Used memory: {} KB", freeMemoryBefore - Runtime.getRuntime()
            .freeMemory());
    }
}

package com.baeldung.extensions.tempdir;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.io.CleanupMode.NEVER;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TemporaryDirectoryWithCleanupUnitTest {

    private Path theTempDirToBeChecked = null;

    @Test
    @Order(1)
    void whenTestMethodWithTempDirNeverCleanup_thenSetInstanceVariable(@TempDir(cleanup = NEVER) Path tempDir) {
        theTempDirToBeChecked = tempDir;
        System.out.println(tempDir.toFile().getAbsolutePath());
    }

    @Test
    @Order(2)
    void whenTestMethodWithTempDirNeverCleanup_thenTempDirShouldNotBeRemoved() {
        assertNotNull(theTempDirToBeChecked);
        assertTrue(theTempDirToBeChecked.toFile().isDirectory());
    }

}
package com.baeldung.junit5.tempdir;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.AnnotatedElementContext;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.io.TempDirFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertTrue;

class TempDirFactoryUnitTest {

    @Test
    void factoryTest(@TempDir(factory = Factory.class) Path tempDir) {
        assertTrue(tempDir.getFileName().toString().startsWith("factoryTest"));
    }

    static class Factory implements TempDirFactory {

        @Override
        public Path createTempDirectory(AnnotatedElementContext elementContext, ExtensionContext extensionContext)
                throws IOException {
            return Files.createTempDirectory(extensionContext.getRequiredTestMethod().getName());
        }

    }

}
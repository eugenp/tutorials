package com.baeldung.learningplatform;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class MavenRuntimeExecUnitTest {

    private static final String PACKAGE_NAME = "com.baeldung.generatedcode";
    private static final String USER_NAME = "john_doe";
    @TempDir
    private Path tempDir;

    @BeforeEach
    public void setUp() throws IOException {
        ProjectBuilder projectBuilder = new ProjectBuilder();
        projectBuilder.build(USER_NAME, tempDir, PACKAGE_NAME);
    }

    @ParameterizedTest
    @MethodSource
    void givenMavenInterface_whenCompileMavenProject_thenCreateTargetDirectory(Maven maven) {
        maven.compile(tempDir);
        assertTrue(Files.exists(tempDir));
    }

    static Stream<Maven> givenMavenInterface_whenCompileMavenProject_thenCreateTargetDirectory() {
        return Stream.of(
          new MavenRuntimeExec(),
          new MavenProcessBuilder(),
          new MavenEmbedder(),
          new MavenInvoker());
    }
}
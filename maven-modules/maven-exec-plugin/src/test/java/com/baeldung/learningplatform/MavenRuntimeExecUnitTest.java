package com.baeldung.learningplatform;

import static com.baeldung.learningplatform.FileUtil.removeDirectoryRecursively;
import static com.baeldung.learningplatform.ProjectsPaths.PROJECT_DIR;
import static com.baeldung.learningplatform.ProjectsPaths.PROJECT_TARGET;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class MavenRuntimeExecUnitTest {

    @BeforeEach
    public void setUp() throws IOException {
        removeDirectoryRecursively(PROJECT_TARGET);
    }

    @ParameterizedTest
    @MethodSource
    void givenMavenInterface_whenCompileMavenProject_thenCreateTargetDirectory(Maven maven) {
        maven.compile(PROJECT_DIR);
        assertTrue(Files.exists(PROJECT_TARGET));
    }

    static Stream<Maven> givenMavenInterface_whenCompileMavenProject_thenCreateTargetDirectory() {
        return Stream.of(
          new MavenRuntimeExec(),
          new MavenProcessBuilder(),
          new MavenEmbedded(),
          new MavenInvoker("/opt/homebrew/bin/mvn"));
    }
}
package com.baeldung.emptiness;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class DirectoryEmptinessUnitTest {

    @Test
    public void givenPath_whenInvalid_thenReturnsFalse() throws IOException {
        assertThat(isEmpty(Paths.get("invalid-addr"))).isFalse();
    }

    @Test
    public void givenPath_whenNotDirectory_thenReturnsFalse() throws IOException {
        Path aFile = Paths.get(getClass().getResource("/notDir.txt").getPath());
        assertThat(isEmpty(aFile)).isFalse();
    }

    @Test
    public void givenPath_whenNotEmptyDir_thenReturnsFalse() throws IOException {
        Path currentDir = new File("").toPath().toAbsolutePath();
        assertThat(isEmpty(currentDir)).isFalse();
    }

    @Test
    public void givenPath_whenIsEmpty_thenReturnsTrue() throws Exception {
        Path path = Files.createTempDirectory("baeldung-empty");
        assertThat(isEmpty(path)).isTrue();
    }

    private static boolean isEmpty(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> directory = Files.newDirectoryStream(path)) {
                return !directory.iterator().hasNext();
            }
        }

        return false;
    }

    private static boolean isEmpty2(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (Stream<Path> entries = Files.list(path)) {
                return !entries.findFirst().isPresent();
            }
        }

        return false;
    }

    private static boolean isEmptyInefficient(Path path) {
        return path.toFile().listFiles().length == 0;
    }
}

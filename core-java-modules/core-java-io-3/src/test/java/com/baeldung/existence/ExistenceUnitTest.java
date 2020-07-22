package com.baeldung.existence;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

public class ExistenceUnitTest {

    @Test
    public void givenFile_whenDoesNotExist_thenFilesReturnsFalse() {
        Path path = Paths.get("does-not-exist.txt");

        assertThat(Files.exists(path)).isFalse();
        assertThat(Files.notExists(path)).isTrue();
    }

    @Test
    public void givenFile_whenExists_thenFilesShouldReturnTrue() throws IOException {
        Path tempFile = Files.createTempFile("baeldung", "exist-nio");
        assertThat(Files.exists(tempFile)).isTrue();
        assertThat(Files.notExists(tempFile)).isFalse();

        Path tempDirectory = Files.createTempDirectory("baeldung-exists");
        assertThat(Files.exists(tempDirectory)).isTrue();
        assertThat(Files.notExists(tempDirectory)).isFalse();

        assertThat(Files.isDirectory(tempDirectory)).isTrue();
        assertThat(Files.isDirectory(tempFile)).isFalse();
        assertThat(Files.isRegularFile(tempFile)).isTrue();

        assertThat(Files.isReadable(tempFile)).isTrue();

        Files.deleteIfExists(tempFile);
        Files.deleteIfExists(tempDirectory);
    }

    @Test
    public void givenSymbolicLink_whenTargetDoesNotExists_thenFollowOrNotBasedOnTheOptions() throws IOException {
        Path target = Files.createTempFile("baeldung", "target");
        Path symbol = Paths.get("test-link-" + ThreadLocalRandom.current().nextInt());

        Path symbolicLink = Files.createSymbolicLink(symbol, target);
        assertThat(Files.exists(symbolicLink)).isTrue();
        assertThat(Files.isSymbolicLink(symbolicLink)).isTrue();
        assertThat(Files.isSymbolicLink(target)).isFalse();

        Files.deleteIfExists(target);
        assertThat(Files.exists(symbolicLink)).isFalse();
        assertThat(Files.exists(symbolicLink, LinkOption.NOFOLLOW_LINKS)).isTrue();

        Files.deleteIfExists(symbolicLink);
    }

    @Test
    public void givenFile_whenDoesNotExist_thenFileReturnsFalse() {
        assertThat(new File("invalid").exists()).isFalse();
        assertThat(new File("invalid").isFile()).isFalse();
    }

    @Test
    public void givenFile_whenExist_thenShouldReturnTrue() throws IOException {
        Path tempFilePath = Files.createTempFile("baeldung", "exist-io");
        Path tempDirectoryPath = Files.createTempDirectory("baeldung-exists-io");

        File tempFile = new File(tempFilePath.toString());
        File tempDirectory = new File(tempDirectoryPath.toString());

        assertThat(tempFile.exists()).isTrue();
        assertThat(tempDirectory.exists()).isTrue();

        assertThat(tempFile.isFile()).isTrue();
        assertThat(tempDirectory.isFile()).isFalse();

        assertThat(tempDirectory.isDirectory()).isTrue();
        assertThat(tempFile.isDirectory()).isFalse();

        assertThat(tempFile.canRead()).isTrue();

        Files.deleteIfExists(tempFilePath);
        Files.deleteIfExists(tempDirectoryPath);
    }
}

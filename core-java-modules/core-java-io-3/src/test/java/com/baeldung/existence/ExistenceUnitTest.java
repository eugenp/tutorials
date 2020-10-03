package com.baeldung.existence;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExistenceUnitTest {

    @Test
    public void givenFile_whenDoesNotExist_thenFilesReturnsFalse() {
        Path path = Paths.get("does-not-exist.txt");

        assertFalse(Files.exists(path));
        assertTrue(Files.notExists(path));
    }

    @Test
    public void givenFile_whenExists_thenFilesShouldReturnTrue() throws IOException {
        Path tempFile = Files.createTempFile("baeldung", "exist-nio");
        assertTrue(Files.exists(tempFile));
        assertFalse(Files.notExists(tempFile));

        Path tempDirectory = Files.createTempDirectory("baeldung-exists");
        assertTrue(Files.exists(tempDirectory));
        assertFalse(Files.notExists(tempDirectory));

        assertTrue(Files.isDirectory(tempDirectory));
        assertFalse(Files.isDirectory(tempFile));
        assertTrue(Files.isRegularFile(tempFile));

        assertTrue(Files.isReadable(tempFile));

        Files.deleteIfExists(tempFile);
        Files.deleteIfExists(tempDirectory);
    }

    @Test
    public void givenSymbolicLink_whenTargetDoesNotExists_thenFollowOrNotBasedOnTheOptions() throws IOException {
        Path target = Files.createTempFile("baeldung", "target");
        Path symbol = Paths.get("test-link-" + ThreadLocalRandom.current().nextInt());
        Path symbolicLink = null;

        try {
            symbolicLink = Files.createSymbolicLink(symbol, target);
        } catch (FileSystemException ex) {
            System.out.println("Your OS security policy prevents the current user from creating symbolic links.\n" +
                    "Most probably you're running Windows with UAC.\n" +
                    "If this is the case, please see - https://docs.microsoft.com/en-us/windows/security/threat-protection/security-policy-settings/create-symbolic-links\n" +
                    "You must change your security settings to run this test under Windows.");
            return;
        }

        assertTrue(Files.exists(symbolicLink));
        assertTrue(Files.isSymbolicLink(symbolicLink));
        assertFalse(Files.isSymbolicLink(target));

        Files.deleteIfExists(target);
        assertFalse(Files.exists(symbolicLink));
        assertTrue(Files.exists(symbolicLink, LinkOption.NOFOLLOW_LINKS));

        Files.deleteIfExists(symbolicLink);
    }

    @Test
    public void givenFile_whenDoesNotExist_thenFileReturnsFalse() {
        assertFalse(new File("invalid").exists());
        assertFalse(new File("invalid").isFile());
    }

    @Test
    public void givenFile_whenExist_thenShouldReturnTrue() throws IOException {
        Path tempFilePath = Files.createTempFile("baeldung", "exist-io");
        Path tempDirectoryPath = Files.createTempDirectory("baeldung-exists-io");

        File tempFile = new File(tempFilePath.toString());
        File tempDirectory = new File(tempDirectoryPath.toString());

        assertTrue(tempFile.exists());
        assertTrue(tempDirectory.exists());

        assertTrue(tempFile.isFile());
        assertFalse(tempDirectory.isFile());

        assertTrue(tempDirectory.isDirectory());
        assertFalse(tempFile.isDirectory());

        assertTrue(tempFile.canRead());

        Files.deleteIfExists(tempFilePath);
        Files.deleteIfExists(tempDirectoryPath);
    }
}

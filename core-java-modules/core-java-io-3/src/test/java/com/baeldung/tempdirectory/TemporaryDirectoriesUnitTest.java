package com.baeldung.tempdirectory;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Tests several possibilities on how to create
 * temporary directories.
 *
 * @author Rui Vilao (rpvilao@gmail.com)
 */
public class TemporaryDirectoriesUnitTest {

    @Test
    public void givenTempDirWithPrefixNoTargetSpecified_whenCreateWithPlainJava_thenInsideOSTempDirStructure() throws IOException {
        final String tmpdir = Files.createTempDirectory("tmpDirPrefix").toFile().getAbsolutePath();
        final String tmpDirsLocation = System.getProperty("java.io.tmpdir");

        assertThat(tmpdir).startsWith(tmpDirsLocation);
    }

    @Test
    public void givenTempDirWithPrefixNoTargetSpecified_whenCreateWithGuava_thenInsideOSTempDirStructure() throws IOException {
        final String tmpdir = com.google.common.io.Files.createTempDir().getAbsolutePath();
        final String tmpDirsLocation = System.getProperty("java.io.tmpdir");

        assertThat(tmpdir).startsWith(tmpDirsLocation);
    }

    @Test
    public void givenTempDirWithPrefixNoTargetSpecified_whenCreateWithApacheCommonsIo_thenInsideOSTempDirStructure() throws IOException {
        final String tmpDirsLocation = System.getProperty("java.io.tmpdir");
        final Path path = Paths.get(FileUtils.getTempDirectory().getAbsolutePath(), UUID.randomUUID().toString());
        final String tmpdir = Files.createDirectories(path).toFile().getAbsolutePath();

        assertThat(tmpdir).startsWith(tmpDirsLocation);
    }

    @Test
    public void givenTempDirWithPrefixWithTargetSpecified_whenCreatePlainJava_thenInsideTarget() throws IOException {
        final Path tmpdir = Files.createTempDirectory(Paths.get("target"), "tmpDirPrefix");
        assertThat(tmpdir.toFile().getPath()).startsWith("target");
    }

    @Test
    public void givenTempDirWithPrefixWithTargetSpecifiedWithDeleteOnExit_whenCreatePlainJava_thenInsideTarget() throws IOException {
        final Path tmpdir = Files.createTempDirectory(Paths.get("target"), "tmpDirPrefix");
        assertThat(tmpdir.toFile().getPath()).startsWith("target");
        tmpdir.toFile().deleteOnExit();
        // we can really assert this test, just as an example.
    }

    @Test
    public void givenTempDirWithPrefixWithFileAttrs_whenCreatePlainJava_thenAttributesAreSet() throws IOException {
        boolean isPosix = FileSystems.getDefault().supportedFileAttributeViews().contains("posix");

        if(!isPosix){
            System.out.println("You must be under a Posix Compliant Filesystem to run this test.");
        } else {
            final FileAttribute<Set<PosixFilePermission>> attrs = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("r--------"));

            final Path tmpdir = Files.createTempDirectory(Paths.get("target"), "tmpDirPrefix", attrs);
            assertThat(tmpdir.toFile().getPath()).startsWith("target");
            assertThat(tmpdir.toFile().canWrite()).isFalse();
        }
    }
}

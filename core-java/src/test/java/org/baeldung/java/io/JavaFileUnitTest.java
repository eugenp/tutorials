package org.baeldung.java.io;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertTrue;

public class JavaFileUnitTest {

    private static final String TEMP_DIR = "src/test/resources/temp" + UUID.randomUUID().toString();

    @BeforeClass
    public static void setup() throws IOException {
        Files.createDirectory(Paths.get(TEMP_DIR));
    }

    @AfterClass
    public static void cleanup() throws IOException {
        FileUtils.deleteDirectory(new File(TEMP_DIR));
    }

    @Test
    public final void givenUsingJDK6_whenCreatingFile_thenCorrect() throws IOException {
        final File newFile = new File(TEMP_DIR + "/newFile_jdk6.txt");
        final boolean success = newFile.createNewFile();

        assertTrue(success);
    }

    @Test
    public final void givenUsingJDK7nio2_whenCreatingFile_thenCorrect() throws IOException {
        final Path newFilePath = Paths.get(TEMP_DIR + "/newFile_jdk7.txt");
        Files.createFile(newFilePath);
    }

    @Test
    public final void givenUsingCommonsIo_whenCreatingFile_thenCorrect() throws IOException {
        FileUtils.touch(new File(TEMP_DIR + "/newFile_commonsio.txt"));
    }

    @Test
    public final void givenUsingGuava_whenCreatingFile_thenCorrect() throws IOException {
        com.google.common.io.Files.touch(new File(TEMP_DIR + "/newFile_guava.txt"));
    }

    // move a file

    @Test
    public final void givenUsingJDK6_whenMovingFile_thenCorrect() throws IOException {
        final File fileToMove = new File(TEMP_DIR + "/toMoveFile_jdk6.txt");
        fileToMove.createNewFile();// .exists();
        final File destDir = new File(TEMP_DIR + "/");
        destDir.mkdir();

        final boolean isMoved = fileToMove.renameTo(new File(TEMP_DIR + "/movedFile_jdk6.txt"));
        if (!isMoved) {
            throw new FileSystemException(TEMP_DIR + "/movedFile_jdk6.txt");
        }
    }

    @Test
    public final void givenUsingJDK7Nio2_whenMovingFile_thenCorrect() throws IOException {
        final Path fileToMovePath = Files.createFile(Paths.get(TEMP_DIR + "/" + randomAlphabetic(5) + ".txt"));
        final Path targetPath = Paths.get(TEMP_DIR + "/");

        Files.move(fileToMovePath, targetPath.resolve(fileToMovePath.getFileName()));
    }

    @Test
    public final void givenUsingGuava_whenMovingFile_thenCorrect() throws IOException {
        final File fileToMove = new File(TEMP_DIR + "/fileToMove.txt");
        fileToMove.createNewFile();
        final File destDir = new File(TEMP_DIR + "/temp");
        final File targetFile = new File(destDir, fileToMove.getName());
        com.google.common.io.Files.createParentDirs(targetFile);
        com.google.common.io.Files.move(fileToMove, targetFile);
    }

    @Test
    public final void givenUsingApache_whenMovingFile_thenCorrect() throws IOException {
        FileUtils.touch(new File(TEMP_DIR + "/fileToMove_apache.txt"));
        FileUtils.moveFile(FileUtils.getFile(TEMP_DIR + "/fileToMove_apache.txt"), FileUtils.getFile(TEMP_DIR + "/fileMoved_apache2.txt"));
    }

    @Test
    public final void givenUsingApache_whenMovingFileApproach2_thenCorrect() throws IOException {
        FileUtils.touch(new File(TEMP_DIR + "/fileToMove_apache.txt"));
        Files.createDirectory(Paths.get(TEMP_DIR + "/temp"));
        FileUtils.moveFileToDirectory(FileUtils.getFile(TEMP_DIR + "/fileToMove_apache.txt"), FileUtils.getFile(TEMP_DIR + "/temp"), true);
    }

    // delete a file

    @Test
    public final void givenUsingJDK6_whenDeletingAFile_thenCorrect() throws IOException {
        new File(TEMP_DIR + "/fileToDelete_jdk6.txt").createNewFile();

        final File fileToDelete = new File(TEMP_DIR + "/fileToDelete_jdk6.txt");
        final boolean success = fileToDelete.delete();

        assertTrue(success);
    }

    @Test
    public final void givenUsingJDK7nio2_whenDeletingAFile_thenCorrect() throws IOException {
        Files.createFile(Paths.get(TEMP_DIR + "/fileToDelete_jdk7.txt"));

        final Path fileToDeletePath = Paths.get(TEMP_DIR + "/fileToDelete_jdk7.txt");
        Files.delete(fileToDeletePath);
    }

    @Test
    public final void givenUsingCommonsIo_whenDeletingAFileV1_thenCorrect() throws IOException {
        FileUtils.touch(new File(TEMP_DIR + "/fileToDelete_commonsIo.txt"));

        final File fileToDelete = FileUtils.getFile(TEMP_DIR + "/fileToDelete_commonsIo.txt");
        final boolean success = FileUtils.deleteQuietly(fileToDelete);

        assertTrue(success);
    }

    @Test
    public void givenUsingCommonsIo_whenDeletingAFileV2_thenCorrect() throws IOException {
        FileUtils.touch(new File(TEMP_DIR + "/fileToDelete.txt"));

        FileUtils.forceDelete(FileUtils.getFile(TEMP_DIR + "/fileToDelete.txt"));
    }

}

package org.baeldung.java.io;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class JavaFileIntegrationTest {

    // create a file

    @Test
    public final void givenUsingJDK6_whenCreatingFile_thenCorrect() throws IOException {
        final File newFile = new File("src/test/resources/newFile_jdk6.txt");
        final boolean success = newFile.createNewFile();

        assertTrue(success);
    }

    @Test
    public final void givenUsingJDK7nio2_whenCreatingFile_thenCorrect() throws IOException {
        final Path newFilePath = Paths.get("src/test/resources/newFile_jdk7.txt");
        Files.createFile(newFilePath);
    }

    @Test
    public final void givenUsingCommonsIo_whenCreatingFile_thenCorrect() throws IOException {
        FileUtils.touch(new File("src/test/resources/newFile_commonsio.txt"));
    }

    @Test
    public final void givenUsingGuava_whenCreatingFile_thenCorrect() throws IOException {
        com.google.common.io.Files.touch(new File("src/test/resources/newFile_guava.txt"));
    }

    // move a file

    @Test
    public final void givenUsingJDK6_whenMovingFile_thenCorrect() throws IOException {
        final File fileToMove = new File("src/test/resources/toMoveFile_jdk6.txt");
        fileToMove.exists();
        final File destDir = new File("src/test/resources/");
        destDir.mkdir();

        final boolean isMoved = fileToMove.renameTo(new File("src/test/resources/movedFile_jdk6.txt"));
        if (!isMoved) {
            throw new FileSystemException("src/test/resources/movedFile_jdk6.txt");
        }
    }

    @Test
    public final void givenUsingJDK7Nio2_whenMovingFile_thenCorrect() throws IOException {
        final Path fileToMovePath = Files.createFile(Paths.get("src/test/resources/" + randomAlphabetic(5) + ".txt"));
        final Path targetPath = Paths.get("src/main/resources/");

        Files.move(fileToMovePath, targetPath.resolve(fileToMovePath.getFileName()));
    }

    @Test
    public final void givenUsingGuava_whenMovingFile_thenCorrect() throws IOException {
        final File fileToMove = new File("src/test/resources/fileToMove.txt");
        fileToMove.createNewFile();
        final File destDir = new File("src/test/resources/");
        final File targetFile = new File(destDir, fileToMove.getName());
        com.google.common.io.Files.createParentDirs(targetFile);
        com.google.common.io.Files.move(fileToMove, targetFile);
    }

    @Test
    public final void givenUsingApache_whenMovingFile_thenCorrect() throws IOException {
        FileUtils.moveFile(FileUtils.getFile("src/test/resources/fileToMove.txt"), FileUtils.getFile("src/test/resources/fileMoved.txt"));
    }

    @Test
    public final void givenUsingApache_whenMovingFileApproach2_thenCorrect() throws IOException {
        FileUtils.touch(new File("src/test/resources/fileToMove.txt"));
        FileUtils.moveFileToDirectory(FileUtils.getFile("src/test/resources/fileToMove.txt"), FileUtils.getFile("src/main/resources/"), true);
    }

    // delete a file

    @Test
    public final void givenUsingJDK6_whenDeletingAFile_thenCorrect() throws IOException {
        new File("src/test/resources/fileToDelete_jdk6.txt").createNewFile();

        final File fileToDelete = new File("src/test/resources/fileToDelete_jdk6.txt");
        final boolean success = fileToDelete.delete();

        assertTrue(success);
    }

    @Test
    public final void givenUsingJDK7nio2_whenDeletingAFile_thenCorrect() throws IOException {
        // Files.createFile(Paths.get("src/test/resources/fileToDelete_jdk7.txt"));

        final Path fileToDeletePath = Paths.get("src/test/resources/fileToDelete_jdk7.txt");
        Files.delete(fileToDeletePath);
    }

    @Test
    public final void givenUsingCommonsIo_whenDeletingAFileV1_thenCorrect() throws IOException {
        FileUtils.touch(new File("src/test/resources/fileToDelete_commonsIo.txt"));

        final File fileToDelete = FileUtils.getFile("src/test/resources/fileToDelete_commonsIo.txt");
        final boolean success = FileUtils.deleteQuietly(fileToDelete);

        assertTrue(success);
    }

    @Test
    public void givenUsingCommonsIo_whenDeletingAFileV2_thenCorrect() throws IOException {
        // FileUtils.touch(new File("src/test/resources/fileToDelete.txt"));

        FileUtils.forceDelete(FileUtils.getFile("src/test/resources/fileToDelete.txt"));
    }

}

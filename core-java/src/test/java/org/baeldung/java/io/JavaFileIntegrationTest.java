package org.baeldung.java.io;

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
    public void givenUsingJDK6_whenCreatingFile_thenCorrect() throws IOException {
        final File newFile = new File("src/test/resources/newFile_jdk6.txt");
        newFile.createNewFile();
    }

    @Test
    public void givenUsingJDK7nio2_whenCreatingFile_thenCorrect() throws IOException {
        final Path newFilePath = Paths.get("src/test/resources/newFile_jdk7.txt");
        Files.createFile(newFilePath);
    }

    @Test
    public void givenUsingApache_whenCreatingFile_thenCorrect() throws IOException {
        FileUtils.touch(new File("src/test/resources/newFile_commonsio.txt"));
    }

    // move a file

    @Test
    public void givenUsingJDK6_whenMovingFile_thenCorrect() throws IOException {
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
    public void givenUsingJDK7Nio2_whenMovingFile_thenCorrect() throws IOException {
        final Path fileToMovePath = Files.createFile(Paths.get("src/test/resources/fileToMove.txt"));
        final Path dirPath = Paths.get("src/test/resources/");
        final Path targetPath = Files.createDirectory(dirPath);

        Files.move(fileToMovePath, targetPath.resolve(fileToMovePath.getFileName()));
    }

    @Test
    public void givenUsingGuava_whenMovingFile_thenCorrect() throws IOException {
        final File fileToMove = new File("src/test/resources/fileToMove.txt");
        fileToMove.createNewFile();
        final File destDir = new File("src/test/resources/");
        final File targetFile = new File(destDir, fileToMove.getName());
        com.google.common.io.Files.createParentDirs(targetFile);
        com.google.common.io.Files.move(fileToMove, targetFile);
    }

    @Test
    public void givenUsingApache_whenMovingFile_thenCorrect() throws IOException {
        FileUtils.moveFile(FileUtils.getFile("src/test/resources/fileToMove.txt"), FileUtils.getFile("src/test/resources/fileMoved.txt"));
    }

    // rename a file

}

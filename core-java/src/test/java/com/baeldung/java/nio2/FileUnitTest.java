package com.baeldung.java.nio2;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.junit.Test;

public class FileUnitTest {
    private static final String HOME = System.getProperty("user.home");

    // checking file or dir
    @Test
    public void givenExistentPath_whenConfirmsFileExists_thenCorrect() {
        final Path p = Paths.get(HOME);
        assertTrue(Files.exists(p));
    }

    @Test
    public void givenNonexistentPath_whenConfirmsFileNotExists_thenCorrect() {
        final Path p = Paths.get(HOME + "/inexistent_file.txt");
        assertTrue(Files.notExists(p));
    }

    @Test
    public void givenExistentDirPath_whenConfirmsNotRegularFile_thenCorrect() {
        final Path p = Paths.get(HOME);
        assertFalse(Files.isRegularFile(p));
    }

    @Test
    public void givenExistentDirPath_whenConfirmsReadable_thenCorrect() {
        final Path p = Paths.get(HOME);
        assertTrue(Files.isReadable(p));
    }

    @Test
    public void givenExistentDirPath_whenConfirmsWritable_thenCorrect() {
        final Path p = Paths.get(HOME);
        assertTrue(Files.isWritable(p));
    }

    @Test
    public void givenExistentDirPath_whenConfirmsExecutable_thenCorrect() {
        final Path p = Paths.get(HOME);
        assertTrue(Files.isExecutable(p));
    }

    @Test
    public void givenSameFilePaths_whenConfirmsIsSame_thenCorrect() throws IOException {
        final Path p1 = Paths.get(HOME);
        final Path p2 = Paths.get(HOME);
        assertTrue(Files.isSameFile(p1, p2));
    }

    // reading, writing and creating files
    // creating file
    @Test
    public void givenFilePath_whenCreatesNewFile_thenCorrect() throws IOException {
        final String fileName = "myfile_" + new Date().getTime() + ".txt";
        final Path p = Paths.get(HOME + "/" + fileName);
        assertFalse(Files.exists(p));
        Files.createFile(p);
        assertTrue(Files.exists(p));

    }

    @Test
    public void givenDirPath_whenCreatesNewDir_thenCorrect() throws IOException {
        final String dirName = "myDir_" + new Date().getTime();
        final Path p = Paths.get(HOME + "/" + dirName);
        assertFalse(Files.exists(p));
        Files.createDirectory(p);
        assertTrue(Files.exists(p));
        assertFalse(Files.isRegularFile(p));
        assertTrue(Files.isDirectory(p));

    }

    @Test
    public void givenDirPath_whenFailsToCreateRecursively_thenCorrect() {
        final String dirName = "myDir_" + new Date().getTime() + "/subdir";
        final Path p = Paths.get(HOME + "/" + dirName);
        assertFalse(Files.exists(p));
        try {
            Files.createDirectory(p);
        } catch (final IOException e) {
            assertTrue(e instanceof NoSuchFileException);
        }
    }

    @Test
    public void givenDirPath_whenCreatesRecursively_thenCorrect() throws IOException {
        final Path dir = Paths.get(HOME + "/myDir_" + new Date().getTime());
        final Path subdir = dir.resolve("subdir");
        assertFalse(Files.exists(dir));
        assertFalse(Files.exists(subdir));
        Files.createDirectories(subdir);
        assertTrue(Files.exists(dir));
        assertTrue(Files.exists(subdir));
    }

    @Test
    public void givenFilePath_whenCreatesTempFile_thenCorrect() throws IOException {
        final String prefix = "log_";
        final String suffix = ".txt";
        Path p = Paths.get(HOME + "/");
        p = Files.createTempFile(p, prefix, suffix);
        // like log_8821081429012075286.txt
        assertTrue(Files.exists(p));

    }

    @Test
    public void givenFilePath_whenCreatesTempFileWithDefaultsNaming_thenCorrect() throws IOException {
        Path p = Paths.get(HOME + "/");
        p = Files.createTempFile(p, null, null);
        // like 8600179353689423985.tmp
        System.out.println(p);
        assertTrue(Files.exists(p));
    }

    @Test
    public void givenNoFilePath_whenCreatesTempFileInTempDir_thenCorrect() throws IOException {
        final Path p = Files.createTempFile(null, null);
        // like C:\Users\new\AppData\Local\Temp\6100927974988978748.tmp
        assertTrue(Files.exists(p));

    }

    // delete file
    @Test
    public void givenPath_whenDeletes_thenCorrect() throws IOException {
        final Path p = Paths.get(HOME + "/fileToDelete.txt");
        assertFalse(Files.exists(p));
        Files.createFile(p);
        assertTrue(Files.exists(p));
        Files.delete(p);
        assertFalse(Files.exists(p));

    }

    @Test
    public void givenPath_whenFailsToDeleteNonEmptyDir_thenCorrect() throws IOException {
        final Path dir = Paths.get(HOME + "/emptyDir" + new Date().getTime());
        Files.createDirectory(dir);
        assertTrue(Files.exists(dir));
        final Path file = dir.resolve("file.txt");
        Files.createFile(file);
        try {
            Files.delete(dir);
        } catch (final IOException e) {
            assertTrue(e instanceof DirectoryNotEmptyException);
        }
        assertTrue(Files.exists(dir));

    }

    @Test
    public void givenInexistentFile_whenDeleteFails_thenCorrect() throws IOException {
        final Path p = Paths.get(HOME + "/inexistentFile.txt");
        assertFalse(Files.exists(p));
        try {
            Files.delete(p);
        } catch (final IOException e) {
            assertTrue(e instanceof NoSuchFileException);
        }

    }

    @Test
    public void givenInexistentFile_whenDeleteIfExistsWorks_thenCorrect() throws IOException {
        final Path p = Paths.get(HOME + "/inexistentFile.txt");
        assertFalse(Files.exists(p));
        Files.deleteIfExists(p);

    }

    // copy file
    @Test
    public void givenFilePath_whenCopiesToNewLocation_thenCorrect() throws IOException {
        final Path dir1 = Paths.get(HOME + "/firstdir_" + new Date().getTime());
        final Path dir2 = Paths.get(HOME + "/otherdir_" + new Date().getTime());
        Files.createDirectory(dir1);
        Files.createDirectory(dir2);
        final Path file1 = dir1.resolve("filetocopy.txt");
        final Path file2 = dir2.resolve("filetocopy.txt");
        Files.createFile(file1);
        assertTrue(Files.exists(file1));
        assertFalse(Files.exists(file2));
        Files.copy(file1, file2);
        assertTrue(Files.exists(file2));

    }

    @Test
    public void givenPath_whenCopyFailsDueToExistingFile_thenCorrect() throws IOException {
        final Path dir1 = Paths.get(HOME + "/firstdir_" + new Date().getTime());
        final Path dir2 = Paths.get(HOME + "/otherdir_" + new Date().getTime());
        Files.createDirectory(dir1);
        Files.createDirectory(dir2);
        final Path file1 = dir1.resolve("filetocopy.txt");
        final Path file2 = dir2.resolve("filetocopy.txt");
        Files.createFile(file1);
        Files.createFile(file2);
        assertTrue(Files.exists(file1));
        assertTrue(Files.exists(file2));
        try {
            Files.copy(file1, file2);
        } catch (final IOException e) {
            assertTrue(e instanceof FileAlreadyExistsException);
        }
        Files.copy(file1, file2, StandardCopyOption.REPLACE_EXISTING);
    }

    // moving files
    @Test
    public void givenFilePath_whenMovesToNewLocation_thenCorrect() throws IOException {
        final Path dir1 = Paths.get(HOME + "/firstdir_" + new Date().getTime());
        final Path dir2 = Paths.get(HOME + "/otherdir_" + new Date().getTime());
        Files.createDirectory(dir1);
        Files.createDirectory(dir2);
        final Path file1 = dir1.resolve("filetocopy.txt");
        final Path file2 = dir2.resolve("filetocopy.txt");
        Files.createFile(file1);
        assertTrue(Files.exists(file1));
        assertFalse(Files.exists(file2));
        Files.move(file1, file2);
        assertTrue(Files.exists(file2));
        assertFalse(Files.exists(file1));

    }

    @Test
    public void givenFilePath_whenMoveFailsDueToExistingFile_thenCorrect() throws IOException {
        final Path dir1 = Paths.get(HOME + "/firstdir_" + new Date().getTime());
        final Path dir2 = Paths.get(HOME + "/otherdir_" + new Date().getTime());
        Files.createDirectory(dir1);
        Files.createDirectory(dir2);
        final Path file1 = dir1.resolve("filetocopy.txt");
        final Path file2 = dir2.resolve("filetocopy.txt");
        Files.createFile(file1);
        Files.createFile(file2);
        assertTrue(Files.exists(file1));
        assertTrue(Files.exists(file2));
        try {
            Files.move(file1, file2);
        } catch (final IOException e) {
            assertTrue(e instanceof FileAlreadyExistsException);
        }
        Files.move(file1, file2, StandardCopyOption.REPLACE_EXISTING);
        assertTrue(Files.exists(file2));
        assertFalse(Files.exists(file1));
    }
}

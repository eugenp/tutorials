package com.baeldung.file;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileIntegrationTest {
    private static final String TEMP_DIR = String.format("%s/temp%s", System.getProperty("user.home"), UUID.randomUUID().toString());

    @BeforeClass
    public static void setup() throws IOException {
        Files.createDirectory(Paths.get(TEMP_DIR));
    }

    @AfterClass
    public static void cleanup() throws IOException {
        FileUtils.deleteDirectory(new File(TEMP_DIR));
    }

    // checking file or dir
    @Test
    public void givenExistentPath_whenConfirmsFileExists_thenCorrect() {
        Path p = Paths.get(TEMP_DIR);
        assertTrue(Files.exists(p));
    }

    @Test
    public void givenNonexistentPath_whenConfirmsFileNotExists_thenCorrect() {
        Path p = Paths.get(TEMP_DIR + "/inexistent_file.txt");
        assertTrue(Files.notExists(p));
    }

    @Test
    public void givenDirPath_whenConfirmsNotRegularFile_thenCorrect() {
        Path p = Paths.get(TEMP_DIR);
        assertFalse(Files.isRegularFile(p));
    }

    @Test
    public void givenExistentDirPath_whenConfirmsReadable_thenCorrect() {
        Path p = Paths.get(TEMP_DIR);
        assertTrue(Files.isReadable(p));
    }

    @Test
    public void givenExistentDirPath_whenConfirmsWritable_thenCorrect() {
        Path p = Paths.get(System.getProperty("user.home"));
        assertTrue(Files.isWritable(p));
    }

    @Test
    public void givenExistentDirPath_whenConfirmsExecutable_thenCorrect() {
        Path p = Paths.get(System.getProperty("user.home"));
        assertTrue(Files.isExecutable(p));
    }

    @Test
    public void givenSameFilePaths_whenConfirmsIsSame_thenCorrect() throws IOException {
        Path p1 = Paths.get(TEMP_DIR);
        Path p2 = Paths.get(TEMP_DIR);
        assertTrue(Files.isSameFile(p1, p2));
    }

    // reading, writing and creating files
    // creating file
    @Test
    public void givenFilePath_whenCreatesNewFile_thenCorrect() throws IOException {
        String fileName = "myfile_" + UUID.randomUUID().toString() + ".txt";
        Path p = Paths.get(TEMP_DIR + "/" + fileName);
        assertFalse(Files.exists(p));
        Files.createFile(p);
        assertTrue(Files.exists(p));

    }

    @Test
    public void givenDirPath_whenCreatesNewDir_thenCorrect() throws IOException {
        String dirName = "myDir_" + UUID.randomUUID().toString();
        Path p = Paths.get(TEMP_DIR + "/" + dirName);
        assertFalse(Files.exists(p));
        Files.createDirectory(p);
        assertTrue(Files.exists(p));
        assertFalse(Files.isRegularFile(p));
        assertTrue(Files.isDirectory(p));

    }

    @Test(expected = NoSuchFileException.class)
    public void givenDirPath_whenFailsToCreateRecursively_thenCorrect() throws IOException {
        String dirName = "myDir_" + UUID.randomUUID().toString() + "/subdir";
        Path p = Paths.get(TEMP_DIR + "/" + dirName);
        assertFalse(Files.exists(p));
        Files.createDirectory(p);

    }

    @Test
    public void givenDirPath_whenCreatesRecursively_thenCorrect() throws IOException {
        Path dir = Paths.get(TEMP_DIR + "/myDir_" + UUID.randomUUID().toString());
        Path subdir = dir.resolve("subdir");
        assertFalse(Files.exists(dir));
        assertFalse(Files.exists(subdir));
        Files.createDirectories(subdir);
        assertTrue(Files.exists(dir));
        assertTrue(Files.exists(subdir));
    }

    @Test
    public void givenFilePath_whenCreatesTempFile_thenCorrect() throws IOException {
        String prefix = "log_";
        String suffix = ".txt";
        Path p = Paths.get(TEMP_DIR + "/");
        p = Files.createTempFile(p, prefix, suffix);
        // like log_8821081429012075286.txt
        assertTrue(Files.exists(p));

    }

    @Test
    public void givenPath_whenCreatesTempFileWithDefaults_thenCorrect() throws IOException {
        Path p = Paths.get(TEMP_DIR + "/");
        p = Files.createTempFile(p, null, null);
        // like 8600179353689423985.tmp
        assertTrue(Files.exists(p));
    }

    @Test
    public void givenNoFilePath_whenCreatesTempFileInTempDir_thenCorrect() throws IOException {
        Path p = Files.createTempFile(null, null);
        // like C:\Users\new\AppData\Local\Temp\6100927974988978748.tmp
        assertTrue(Files.exists(p));

    }

    // delete file
    @Test
    public void givenPath_whenDeletes_thenCorrect() throws IOException {
        Path p = Paths.get(TEMP_DIR + "/fileToDelete.txt");
        assertFalse(Files.exists(p));
        Files.createFile(p);
        assertTrue(Files.exists(p));
        Files.delete(p);
        assertFalse(Files.exists(p));

    }

    @Test(expected = DirectoryNotEmptyException.class)
    public void givenPath_whenFailsToDeleteNonEmptyDir_thenCorrect() throws IOException {
        Path dir = Paths.get(TEMP_DIR + "/emptyDir" + UUID.randomUUID().toString());
        Files.createDirectory(dir);
        assertTrue(Files.exists(dir));
        Path file = dir.resolve("file.txt");
        Files.createFile(file);
        Files.delete(dir);

        assertTrue(Files.exists(dir));

    }

    @Test(expected = NoSuchFileException.class)
    public void givenInexistentFile_whenDeleteFails_thenCorrect() throws IOException {
        Path p = Paths.get(TEMP_DIR + "/inexistentFile.txt");
        assertFalse(Files.exists(p));
        Files.delete(p);

    }

    @Test
    public void givenInexistentFile_whenDeleteIfExistsWorks_thenCorrect() throws IOException {
        Path p = Paths.get(TEMP_DIR + "/inexistentFile.txt");
        assertFalse(Files.exists(p));
        Files.deleteIfExists(p);

    }

    // copy file
    @Test
    public void givenFilePath_whenCopiesToNewLocation_thenCorrect() throws IOException {
        Path dir1 = Paths.get(TEMP_DIR + "/firstdir_" + UUID.randomUUID().toString());
        Path dir2 = Paths.get(TEMP_DIR + "/otherdir_" + UUID.randomUUID().toString());
        Files.createDirectory(dir1);
        Files.createDirectory(dir2);
        Path file1 = dir1.resolve("filetocopy.txt");
        Path file2 = dir2.resolve("filetocopy.txt");
        Files.createFile(file1);
        assertTrue(Files.exists(file1));
        assertFalse(Files.exists(file2));
        Files.copy(file1, file2);
        assertTrue(Files.exists(file2));

    }

    @Test(expected = FileAlreadyExistsException.class)
    public void givenPath_whenCopyFailsDueToExistingFile_thenCorrect() throws IOException {
        Path dir1 = Paths.get(TEMP_DIR + "/firstdir_" + UUID.randomUUID().toString());
        Path dir2 = Paths.get(TEMP_DIR + "/otherdir_" + UUID.randomUUID().toString());
        Files.createDirectory(dir1);
        Files.createDirectory(dir2);
        Path file1 = dir1.resolve("filetocopy.txt");
        Path file2 = dir2.resolve("filetocopy.txt");
        Files.createFile(file1);
        Files.createFile(file2);
        assertTrue(Files.exists(file1));
        assertTrue(Files.exists(file2));
        Files.copy(file1, file2);
        Files.copy(file1, file2, StandardCopyOption.REPLACE_EXISTING);
    }

    // moving files
    @Test
    public void givenFilePath_whenMovesToNewLocation_thenCorrect() throws IOException {
        Path dir1 = Paths.get(TEMP_DIR + "/firstdir_" + UUID.randomUUID().toString());
        Path dir2 = Paths.get(TEMP_DIR + "/otherdir_" + UUID.randomUUID().toString());
        Files.createDirectory(dir1);
        Files.createDirectory(dir2);
        Path file1 = dir1.resolve("filetocopy.txt");
        Path file2 = dir2.resolve("filetocopy.txt");
        Files.createFile(file1);
        assertTrue(Files.exists(file1));
        assertFalse(Files.exists(file2));
        Files.move(file1, file2);
        assertTrue(Files.exists(file2));
        assertFalse(Files.exists(file1));

    }

    @Test(expected = FileAlreadyExistsException.class)
    public void givenFilePath_whenMoveFailsDueToExistingFile_thenCorrect() throws IOException {
        Path dir1 = Paths.get(TEMP_DIR + "/firstdir_" + UUID.randomUUID().toString());
        Path dir2 = Paths.get(TEMP_DIR + "/otherdir_" + UUID.randomUUID().toString());
        Files.createDirectory(dir1);
        Files.createDirectory(dir2);
        Path file1 = dir1.resolve("filetocopy.txt");
        Path file2 = dir2.resolve("filetocopy.txt");
        Files.createFile(file1);
        Files.createFile(file2);
        assertTrue(Files.exists(file1));
        assertTrue(Files.exists(file2));
        Files.move(file1, file2);
        Files.move(file1, file2, StandardCopyOption.REPLACE_EXISTING);
        assertTrue(Files.exists(file2));
        assertFalse(Files.exists(file1));
    }

}

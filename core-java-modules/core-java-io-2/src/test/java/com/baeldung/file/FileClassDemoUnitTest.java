package com.baeldung.file;

import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FileClassDemoUnitTest {

    @Test
    public void givenDirCreated_whenMkdir_thenDirIsDeleted() {
        File directory = new File("dir");
        assertTrue(directory.mkdir());
        assertTrue(directory.delete());
    }

    @Test
    public void givenFileCreated_whenCreateNewFile_thenFileIsDeleted() throws IOException {
        File file = new File("file.txt");
        assertTrue(file.createNewFile());
        assertTrue(file.delete());
    }

    @Test
    public void givenFileCreated_whenCreateNewFile_thenMetadataIsAsExpected() throws IOException {

        // different Operating systems have different separator characters
        String sep = System.getProperty("file.separator");

        File parentDir = makeDir("filesDir");

        File child = new File(parentDir, "file.txt");
        child.createNewFile();

        assertEquals("file.txt", child.getName());
        assertEquals(parentDir.getName(), child.getParentFile().getName());
        assertEquals(parentDir.getPath() + sep + "file.txt", child.getPath());

        removeDir(parentDir);
    }

    @Test(expected = FileNotFoundException.class)
    public void givenReadOnlyFileCreated_whenCreateNewFile_thenCannotModifyFile() throws IOException {
        File parentDir = makeDir("readDir");

        File child = new File(parentDir, "file.txt");
        child.createNewFile();
        child.setWritable(false);

        try (FileOutputStream fos = new FileOutputStream(child)) {
            fos.write("Hello World".getBytes()); // write operation
            fos.flush();
        } finally {
            removeDir(parentDir);
        }
    }

    @Test(expected = FileNotFoundException.class)
    public void givenWriteOnlyFileCreated_whenCreateNewFile_thenFileCannotBeRead() throws IOException {
        File parentDir = makeDir("writeDir");

        File child = new File(parentDir, "file.txt");
        child.createNewFile();
        child.setReadable(false);

        try (FileInputStream fis = new FileInputStream(child);) {
            fis.read(); // read operation
        } finally {
            removeDir(parentDir);
        }
    }

    @Test
    public void givenFilesCreatedInDir_whenCreateNewFile_thenFilesCanBeListed() throws IOException {
        File parentDir = makeDir("filtersDir");

        String[] files = {"file1.csv", "file2.txt"};
        for (String file : files) {
            new File(parentDir, file).createNewFile();
        }

        //normal listing
        assertEquals(2, parentDir.list().length);

        //filtered listing
        FilenameFilter csvFilter = (dir, ext) -> ext.endsWith(".csv");
        assertEquals(1, parentDir.list(csvFilter).length);

        removeDir(parentDir);
    }

    @Test
    public void givenDirCreated_whenMkdir_thenDirCanBeRenamed() {

        File source = makeDir("source");
        File destination = makeDir("destination");
        boolean renamed = source.renameTo(destination);

        if (renamed) {
            assertFalse(source.isDirectory());
            assertTrue(destination.isDirectory());

            removeDir(destination);
        }
    }

    @Test
    public void givenDataWrittenToFile_whenWrite_thenFreeSpaceOnSystemReduces() throws IOException {

        String home = System.getProperty("user.home");
        String sep = System.getProperty("file.separator");
        File testDir = makeDir(home + sep + "test");
        File sample = new File(testDir, "sample.txt");

        long freeSpaceBefore = testDir.getFreeSpace();
        writeSampleDataToFile(sample);

        long freeSpaceAfter = testDir.getFreeSpace();
        assertTrue(freeSpaceAfter < freeSpaceBefore);

        removeDir(testDir);
    }

    private static File makeDir(String name) {
        File directory = new File(name);
        directory.mkdir();
        if (directory.isDirectory()) {
            return directory;
        }
        throw new RuntimeException("'" + name + "' not made!");
    }

    private static void removeDir(File directory) {
        // make sure you don't delete your home directory here
        String home = System.getProperty("user.home");
        if (directory.getPath().equals(home)) {
            return;
        }

        // remove directory and its files from system
        if (directory.exists()) {
            // delete all files inside the directory
            File[] dirFiles = directory.listFiles();
            if (dirFiles != null) {
                List<File> files = Arrays.asList(dirFiles);
                files.forEach(f -> deleteFile(f));
            }

            // finally delete the directory itself
            deleteFile(directory);
        }
    }

    private static void deleteFile(File fileToDelete) {
        if (fileToDelete != null && fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }

    private static void writeSampleDataToFile(File sample) throws IOException {
        //write sample text to file
        try (FileOutputStream out = new FileOutputStream(sample)) {
            for (int i = 1; i <= 100000; i++) {
                String text = "Sample line number " + i + "\n";
                out.write(text.getBytes());
            }
        }
    }
}

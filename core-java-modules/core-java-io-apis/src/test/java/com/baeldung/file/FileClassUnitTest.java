package com.baeldung.file;

import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FileClassUnitTest {

    @Test
    public void givenDir_whenMkdir_thenDirIsDeleted() {
        File directory = new File("dir");
        assertTrue(directory.mkdir());
        assertTrue(directory.delete());
    }

    @Test
    public void givenFile_whenCreateNewFile_thenFileIsDeleted() {
        File file = new File("file.txt");
        try {
            assertTrue(file.createNewFile());
        } catch (IOException e) {
            fail("Could not create " + "file.txt");
        }
        assertTrue(file.delete());
    }

    @Test
    public void givenFile_whenCreateNewFile_thenMetadataIsCorrect() {

        String sep = File.separator;

        File parentDir = makeDir("filesDir");

        File child = new File(parentDir, "file.txt");
        try {
            child.createNewFile();
        } catch (IOException e) {
            fail("Could not create " + "file.txt");
        }

        assertEquals("file.txt", child.getName());
        assertEquals(parentDir.getName(), child.getParentFile().getName());
        assertEquals(parentDir.getPath() + sep + "file.txt", child.getPath());

        removeDir(parentDir);
    }


    @Test
    public void givenReadOnlyFile_whenCreateNewFile_thenCantModFile() {
        File parentDir = makeDir("readDir");

        File child = new File(parentDir, "file.txt");
        try {
            child.createNewFile();
        } catch (IOException e) {
            fail("Could not create " + "file.txt");
        }
        child.setWritable(false);
        boolean writable = true;
        try (FileOutputStream fos = new FileOutputStream(child)) {
            fos.write("Hello World".getBytes()); // write operation
            fos.flush();
        } catch (IOException e) {
            writable = false;
        } finally {
            removeDir(parentDir);
        }
        assertFalse(writable);
    }

    @Ignore
    @Test
    public void givenWriteOnlyFile_whenCreateNewFile_thenCantReadFile() {
        File parentDir = makeDir("writeDir");

        File child = new File(parentDir, "file.txt");
        try {
            child.createNewFile();
        } catch (IOException e) {
            fail("Could not create " + "file.txt");
        }
        child.setReadable(false);
        boolean readable = true;
        try (FileInputStream fis = new FileInputStream(child)) {
            fis.read(); // read operation
        } catch (IOException e) {
            readable = false;
        } finally {
            removeDir(parentDir);
        }
        assertFalse(readable);
    }

    @Test
    public void givenFilesInDir_whenCreateNewFile_thenCanListFiles() {
        File parentDir = makeDir("filtersDir");

        String[] files = {"file1.csv", "file2.txt"};
        for (String file : files) {
            try {
                new File(parentDir, file).createNewFile();
            } catch (IOException e) {
                fail("Could not create " + file);
            }
        }

        //normal listing
        assertEquals(2, parentDir.list().length);

        //filtered listing
        FilenameFilter csvFilter = (dir, ext) -> ext.endsWith(".csv");
        assertEquals(1, parentDir.list(csvFilter).length);

        removeDir(parentDir);
    }

    @Test
    public void givenDir_whenMkdir_thenCanRenameDir() {

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
    public void givenDataWritten_whenWrite_thenFreeSpaceReduces() {

        String home = System.getProperty("user.home");
        String sep = File.separator;
        File testDir = makeDir(home + sep + "test");
        File sample = new File(testDir, "sample.txt");

        long freeSpaceBefore = testDir.getFreeSpace();
        try {
            writeSampleDataToFile(sample);
        } catch (IOException e) {
            fail("Could not write to " + "sample.txt");
        }

        long freeSpaceAfter = testDir.getFreeSpace();
        assertTrue(freeSpaceAfter < freeSpaceBefore);

        removeDir(testDir);
    }

    private static File makeDir(String name) {
        File directory = new File(name);

        // If the directory already exists, make sure we create it 'from scratch', i.e. all the files inside are deleted first
        if (directory.exists()) {
            removeDir(directory);
        }

        if (directory.mkdir()) {
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
package com.baeldung.io;

import org.junit.After;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileClassDemoTest {

    private File directory;

    @After
    public void tearUp() {
        if (directory != null && directory.exists()) {
            // delete all files inside the directory
            List<File> files = Arrays.asList(directory.listFiles());
            files.forEach(f -> deleteFile(f));

            // finally delete the directory itself
            deleteFile(directory);
        }
    }


    @Test
    public void checkIfFileExists_ForTheCreatedFileInstance() throws IOException {

        directory = FileClassDemo.withPathNameOnly("filesDirectory");
        directory.mkdir();

        if (directory.isDirectory()) {
            File file = FileClassDemo.withParent(directory, "test.txt");
            file.createNewFile();

            assertTrue(file.exists());
            assertTrue(file.isFile());
        }
    }

    @Test
    public void testIfMetadataIsCorrect() throws IOException {

        // different Operating systems have different separator characters
        String separatorCharacter = System.getProperty("file.separator");

        directory = FileClassDemo.withPathNameOnly("filesDirectory");
        directory.mkdir();

        if (directory.isDirectory()) {
            File file = FileClassDemo.withParent(directory, "test.txt");
            file.createNewFile();

            assertEquals("test.txt", file.getName());
            assertEquals(directory, file.getParentFile());
            assertEquals(directory.getPath() + separatorCharacter + "test.txt", file.getPath());
        }
    }

    @Test(expected = FileNotFoundException.class)
    public void testThatFileCannotBeWrittenTo() throws IOException {
        directory = FileClassDemo.withPathNameOnly("filesDirectory");
        directory.mkdir();

        if (directory.isDirectory()) {
            File file = FileClassDemo.withParent(directory, "test.txt");
            file.createNewFile();

            file.setWritable(false);

            FileOutputStream fos = new FileOutputStream(file);
            fos.write("Hello World".getBytes()); // write operation
            fos.flush();
            fos.close();
        }
    }

    @Test(expected = FileNotFoundException.class)
    public void testThatFileCannotBeReadFrom() throws IOException {
        directory = FileClassDemo.withPathNameOnly("filesDirectory");
        directory.mkdir();

        if (directory.isDirectory()) {
            File file = FileClassDemo.withParent(directory, "test.txt");
            file.createNewFile();

            file.setReadable(false);

            FileInputStream fis = new FileInputStream(file);
            fis.read(); // read operation
            fis.close();
        }
    }

    private void deleteFile(File fileToDelete) {
        if (fileToDelete != null && fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }

}
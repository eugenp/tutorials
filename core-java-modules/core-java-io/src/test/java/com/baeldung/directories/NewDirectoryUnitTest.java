package com.baeldung.directories;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NewDirectoryUnitTest {

    private static final File TEMP_DIRECTORY = new File(System.getProperty("java.io.tmpdir"));

    @Before
    public void beforeEach() {
        File newDirectory = new File(TEMP_DIRECTORY, "new_directory");
        File nestedInNewDirectory = new File(newDirectory, "nested_directory");
        File existingDirectory = new File(TEMP_DIRECTORY, "existing_directory");
        File existingNestedDirectory = new File(existingDirectory, "existing_nested_directory");
        File nestedInExistingDirectory = new File(existingDirectory, "nested_directory");

        nestedInNewDirectory.delete();
        newDirectory.delete();
        nestedInExistingDirectory.delete();
        existingDirectory.mkdir();
        existingNestedDirectory.mkdir();
    }

    @Test
    public void givenUnexistingDirectory_whenMkdir_thenTrue() {
        File newDirectory = new File(TEMP_DIRECTORY, "new_directory");
        assertFalse(newDirectory.exists());

        boolean directoryCreated = newDirectory.mkdir();

        assertTrue(directoryCreated);
    }

    @Test
    public void givenExistingDirectory_whenMkdir_thenFalse() {
        File newDirectory = new File(TEMP_DIRECTORY, "new_directory");
        newDirectory.mkdir();
        assertTrue(newDirectory.exists());

        boolean directoryCreated = newDirectory.mkdir();

        assertFalse(directoryCreated);
    }

    @Test
    public void givenUnexistingNestedDirectories_whenMkdir_thenFalse() {
        File newDirectory = new File(TEMP_DIRECTORY, "new_directory");
        File nestedDirectory = new File(newDirectory, "nested_directory");
        assertFalse(newDirectory.exists());
        assertFalse(nestedDirectory.exists());

        boolean directoriesCreated = nestedDirectory.mkdir();

        assertFalse(directoriesCreated);
    }

    @Test
    public void givenUnexistingNestedDirectories_whenMkdirs_thenTrue() {
        File newDirectory = new File(System.getProperty("java.io.tmpdir") + File.separator + "new_directory");
        File nestedDirectory = new File(newDirectory, "nested_directory");
        assertFalse(newDirectory.exists());
        assertFalse(nestedDirectory.exists());

        boolean directoriesCreated = nestedDirectory.mkdirs();

        assertTrue(directoriesCreated);
    }

    @Test
    public void givenExistingParentDirectories_whenMkdirs_thenTrue() {
        File newDirectory = new File(TEMP_DIRECTORY, "existing_directory");
        newDirectory.mkdir();
        File nestedDirectory = new File(newDirectory, "nested_directory");
        assertTrue(newDirectory.exists());
        assertFalse(nestedDirectory.exists());

        boolean directoriesCreated = nestedDirectory.mkdirs();

        assertTrue(directoriesCreated);
    }

    @Test
    public void givenExistingNestedDirectories_whenMkdirs_thenFalse() {
        File existingDirectory = new File(TEMP_DIRECTORY, "existing_directory");
        File existingNestedDirectory = new File(existingDirectory, "existing_nested_directory");
        assertTrue(existingDirectory.exists());
        assertTrue(existingNestedDirectory.exists());

        boolean directoriesCreated = existingNestedDirectory.mkdirs();

        assertFalse(directoriesCreated);
    }

}

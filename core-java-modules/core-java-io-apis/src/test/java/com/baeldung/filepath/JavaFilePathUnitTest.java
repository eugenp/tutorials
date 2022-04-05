package com.baeldung.filepath;

import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JavaFilePathUnitTest {

    private static String userDir;

    @BeforeClass
    public static void createFilesAndFolders() throws IOException {
        userDir = System.getProperty("user.dir");

        new File(userDir + "/baeldung/foo").mkdirs();
        new File(userDir + "/baeldung/bar/baz").mkdirs();
        new File(userDir + "/baeldung/foo/foo-one.txt").createNewFile();
        new File(userDir + "/baeldung/foo/foo-two.txt").createNewFile();
        new File(userDir + "/baeldung/bar/bar-one.txt").createNewFile();
        new File(userDir + "/baeldung/bar/bar-two.txt").createNewFile();
        new File(userDir + "/baeldung/bar/baz/baz-one.txt").createNewFile();
        new File(userDir + "/baeldung/bar/baz/baz-two.txt").createNewFile();

    }

    @Test
    public void whenPathResolved_thenSuccess() {
        File file = new File("baeldung/foo/foo-one.txt");
        String expectedPath = isWindows() ? "baeldung\\foo\\foo-one.txt" : "baeldung/foo/foo-one.txt";
        String actualPath = file.getPath();
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void whenAbsolutePathResolved_thenSuccess() {
        File file = new File("baeldung/foo/foo-one.txt");
        String expectedPath = isWindows() ? userDir + "\\baeldung\\foo\\foo-one.txt" : userDir + "/baeldung/foo/foo-one.txt";
        String actualPath = file.getAbsolutePath();
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void whenAbsolutePathWithShorthandResolved_thenSuccess() {
        File file = new File("baeldung/bar/baz/../bar-one.txt");
        String expectedPath = isWindows() ? userDir + "\\baeldung\\bar\\baz\\..\\bar-one.txt" : userDir + "/baeldung/bar/baz/../bar-one.txt";
        String actualPath = file.getAbsolutePath();
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void whenCanonicalPathWithShorthandResolved_thenSuccess() throws IOException {
        File file = new File("baeldung/bar/baz/../bar-one.txt");
        String expectedPath = isWindows() ? userDir + "\\baeldung\\bar\\bar-one.txt" : userDir + "/baeldung/bar/bar-one.txt";
        String actualPath = file.getCanonicalPath();
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void whenCanonicalPathWithDotShorthandResolved_thenSuccess() throws IOException {
        File file = new File("baeldung/bar/baz/./baz-one.txt");
        String expectedPath = isWindows() ? userDir + "\\baeldung\\bar\\baz\\baz-one.txt" : userDir + "/baeldung/bar/baz/baz-one.txt";
        String actualPath = file.getCanonicalPath();
        assertEquals(expectedPath, actualPath);
    }

    @Test(expected = IOException.class)
    public void givenWindowsOs_whenCanonicalPathWithWildcard_thenIOException() throws IOException {
        Assume.assumeTrue(isWindows());
        new File("*").getCanonicalPath();
    }

    @AfterClass
    public static void deleteFilesAndFolders() {
        File baeldungDir = new File(userDir + "/baeldung");
        deleteRecursively(baeldungDir);
    }

    private static void deleteRecursively(File dir) {
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                deleteRecursively(f);
            }
            f.delete();
        }
        dir.delete();
    }

    private static boolean isWindows() {
        String osName = System.getProperty("os.name");
        return osName.contains("Windows");
    }

}

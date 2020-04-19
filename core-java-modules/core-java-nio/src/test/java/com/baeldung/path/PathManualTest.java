package com.baeldung.path;

import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class PathManualTest {

    private static final String HOME = System.getProperty("user.home");

    // creating a path
    @Test
    public void givenPathString_whenCreatesPathObject_thenCorrect() {
        Path p = Paths.get("/articles/baeldung");
        assertEquals("\\articles\\baeldung", p.toString());

    }

    @Test
    public void givenPathParts_whenCreatesPathObject_thenCorrect() {
        Path p = Paths.get("/articles", "baeldung");
        assertEquals("\\articles\\baeldung", p.toString());

    }

    // retrieving path info
    @Test
    public void givenPath_whenRetrievesFileName_thenCorrect() {
        Path p = Paths.get("/articles/baeldung/logs");
        assertEquals("logs", p.getFileName().toString());
    }

    @Test
    public void givenPath_whenRetrievesNameByIndex_thenCorrect() {
        Path p = Paths.get("/articles/baeldung/logs");
        assertEquals("articles", p.getName(0).toString());
        assertEquals("baeldung", p.getName(1).toString());
        assertEquals("logs", p.getName(2).toString());
    }

    @Test
    public void givenPath_whenCountsParts_thenCorrect() {
        Path p = Paths.get("/articles/baeldung/logs");
        assertEquals(3, p.getNameCount());
    }

    @Test
    public void givenPath_whenCanRetrieveSubsequenceByIndex_thenCorrect() {
        Path p = Paths.get("/articles/baeldung/logs");
        assertEquals("articles", p.subpath(0, 1).toString());
        assertEquals("articles\\baeldung", p.subpath(0, 2).toString());
        assertEquals("articles\\baeldung\\logs", p.subpath(0, 3).toString());
        assertEquals("baeldung", p.subpath(1, 2).toString());
        assertEquals("baeldung\\logs", p.subpath(1, 3).toString());
        assertEquals("logs", p.subpath(2, 3).toString());
    }

    @Test
    public void givenPath_whenRetrievesParent_thenCorrect() {
        Path p1 = Paths.get("/articles/baeldung/logs");
        Path p2 = Paths.get("/articles/baeldung");
        Path p3 = Paths.get("/articles");
        Path p4 = Paths.get("/");

        assertEquals("\\articles\\baeldung", p1.getParent().toString());
        assertEquals("\\articles", p2.getParent().toString());
        assertEquals("\\", p3.getParent().toString());
        assertEquals(null, p4.getParent());
    }

    @Test
    public void givenPath_whenRetrievesRoot_thenCorrect() {
        Path p1 = Paths.get("/articles/baeldung/logs");
        Path p2 = Paths.get("c:/articles/baeldung/logs");

        assertEquals("\\", p1.getRoot().toString());
        assertEquals("c:\\", p2.getRoot().toString());
    }

    // removing redundancies from path
    @Test
    public void givenPath_whenRemovesRedundancies_thenCorrect1() {
        Path p = Paths.get("/home/./baeldung/articles");
        p = p.normalize();
        assertEquals("\\home\\baeldung\\articles", p.toString());
    }

    @Test
    public void givenPath_whenRemovesRedundancies_thenCorrect2() {
        Path p = Paths.get("/home/baeldung/../articles");
        p = p.normalize();
        assertEquals("\\home\\articles", p.toString());
    }

    // converting a path
    @Test
    public void givenPath_whenConvertsToBrowseablePath_thenCorrect() {
        Path p = Paths.get("/home/baeldung/articles.html");
        URI uri = p.toUri();
        assertEquals("file:///E:/home/baeldung/articles.html", uri.toString());
    }

    @Test
    public void givenPath_whenConvertsToAbsolutePath_thenCorrect() {
        Path p = Paths.get("/home/baeldung/articles.html");
        assertEquals("E:\\home\\baeldung\\articles.html", p.toAbsolutePath().toString());
    }

    @Test
    public void givenAbsolutePath_whenRetainsAsAbsolute_thenCorrect() {
        Path p = Paths.get("E:\\home\\baeldung\\articles.html");
        assertEquals("E:\\home\\baeldung\\articles.html", p.toAbsolutePath().toString());
    }

    @Test
    public void givenExistingPath_whenGetsRealPathToFile_thenCorrect() throws IOException {
        Path p = Paths.get(HOME);
        assertEquals(HOME, p.toRealPath().toString());
    }

    @Test(expected = NoSuchFileException.class)
    public void givenInExistentPath_whenFailsToConvert_thenCorrect() throws IOException {
        Path p = Paths.get("E:\\home\\baeldung\\articles.html");

        p.toRealPath();
    }

    // joining paths
    @Test
    public void givenTwoPaths_whenJoinsAndResolves_thenCorrect() throws IOException {
        Path p = Paths.get("/baeldung/articles");
        assertEquals("\\baeldung\\articles\\java", p.resolve("java").toString());
    }

    @Test
    public void givenAbsolutePath_whenResolutionRetainsIt_thenCorrect() throws IOException {
        Path p = Paths.get("/baeldung/articles");
        assertEquals("C:\\baeldung\\articles\\java", p.resolve("C:\\baeldung\\articles\\java").toString());
    }

    @Test
    public void givenPathWithRoot_whenResolutionRetainsIt_thenCorrect2() throws IOException {
        Path p = Paths.get("/baeldung/articles");
        assertEquals("\\java", p.resolve("/java").toString());
    }

    // creating a path between 2 paths
    @Test
    public void givenSiblingPaths_whenCreatesPathToOther_thenCorrect() throws IOException {
        Path p1 = Paths.get("articles");
        Path p2 = Paths.get("authors");
        assertEquals("..\\authors", p1.relativize(p2).toString());
        assertEquals("..\\articles", p2.relativize(p1).toString());
    }

    @Test
    public void givenNonSiblingPaths_whenCreatesPathToOther_thenCorrect() throws IOException {
        Path p1 = Paths.get("/baeldung");
        Path p2 = Paths.get("/baeldung/authors/articles");
        assertEquals("authors\\articles", p1.relativize(p2).toString());
        assertEquals("..\\..", p2.relativize(p1).toString());
    }

    // comparing 2 paths
    @Test
    public void givenTwoPaths_whenTestsEquality_thenCorrect() throws IOException {
        Path p1 = Paths.get("/baeldung/articles");
        Path p2 = Paths.get("/baeldung/articles");
        Path p3 = Paths.get("/baeldung/authors");

        assertTrue(p1.equals(p2));
        assertFalse(p1.equals(p3));
    }

    @Test
    public void givenPath_whenInspectsStart_thenCorrect() {
        Path p1 = Paths.get("/baeldung/articles");
        assertTrue(p1.startsWith("/baeldung"));
    }

    @Test
    public void givenPath_whenInspectsEnd_thenCorrect() {
        Path p1 = Paths.get("/baeldung/articles");
        assertTrue(p1.endsWith("articles"));
    }
}

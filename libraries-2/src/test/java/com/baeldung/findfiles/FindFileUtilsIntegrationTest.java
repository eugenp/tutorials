package com.baeldung.findfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FindFileUtilsIntegrationTest {

    private static final String TEST_EXTENSION = ".test";
    private static final String OTHER_EXTENSION = ".other";

    private static final List<Path> TEST_FILES = new ArrayList<>();
    private static final List<Path> OTHER_FILES = new ArrayList<>();

    private static Path TEST_DIR;

    @BeforeAll
    static void setup() throws IOException {
        TEST_DIR = Files.createTempDirectory(null);

        final Path nestedDir = TEST_DIR.resolve("sub-dir");
        Files.createDirectories(nestedDir);
        
        TEST_FILES.add(Files.createFile(TEST_DIR.resolve("a" + TEST_EXTENSION)));
        OTHER_FILES.add(Files.createFile(TEST_DIR.resolve("a" + OTHER_EXTENSION)));

        TEST_FILES.add(Files.createFile(nestedDir.resolve("b" + TEST_EXTENSION)));
        OTHER_FILES.add(Files.createFile(nestedDir.resolve("b" + OTHER_EXTENSION)));
    }

    @AfterAll
    static void cleanUp() {
        FileUtils.deleteQuietly(TEST_DIR.toFile());
    }

    @Test
    void whenFindFilesWithJava2_thenOnlyMatchingFilesFound() {
        List<File> matches = FindFileJava2Utils.find(TEST_DIR.toFile(), TEST_EXTENSION);

        assertEquals(TEST_FILES.size(), matches.size());
    }

    @Test
    void whenFindFilesWithJava7_thenOnlyMatchingFilesFound() throws IOException {
        List<Path> matches = FindFileJava7Utils.find(TEST_DIR, TEST_EXTENSION);

        assertEquals(TEST_FILES.size(), matches.size());
    }

    @Test
    void whenFindFilesWithJava8_thenOnlyMatchingFilesFound() throws IOException {
        final AtomicInteger matches = new AtomicInteger(0);
        FindFileJava8Utils.find(TEST_DIR, TEST_EXTENSION, path -> matches.incrementAndGet());

        assertEquals(TEST_FILES.size(), matches.get());
    }

    @Test
    void whenFindFilesWithApache_thenOnlyMatchingFilesFound() {
        final AtomicInteger matches = new AtomicInteger(0);
        Iterator<File> iterator = FindFileApacheUtils.find(TEST_DIR, TEST_EXTENSION);

        iterator.forEachRemaining(file -> matches.incrementAndGet());

        assertEquals(TEST_FILES.size(), matches.get());
    }
}

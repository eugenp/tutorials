package com.baeldung.paralleltesting;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

@Tag("parallel")
@Tag("integration")
public class TestFolderCreator1 {

    private long start;
    private static long startAll;

    private Path baseFolder = Paths.get(getClass().getResource("/")
        .getPath());

    private Integer workerID = Integer.valueOf(System.getProperty("org.gradle.test.worker", "1"));
    private String testFolderName = "/" + "Test_" + workerID;

    @BeforeAll
    static void beforeAll() {
        startAll = Instant.now()
            .toEpochMilli();
    }

    @AfterAll
    static void afterAll() {
        long endAll = Instant.now()
            .toEpochMilli();
        System.out.println("Total time: " + (endAll - startAll) + " ms");
    }

    @BeforeEach
    void setUp() {
        start = Instant.now()
            .toEpochMilli();
        //preemptive clean up with helper function
        removeTestFolder();
    }

    @Test
    void whenCreated_ThenCorrect() throws IOException, InterruptedException {
        FolderCreator folderCreator = new FolderCreator();
        assertTrue(folderCreator.createFolder(baseFolder, testFolderName));
        Thread.sleep(1000L);
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        long end = Instant.now()
            .toEpochMilli();
        System.out.println("Class " + getClass().getSimpleName() + " checks folder " + testFolderName + " started at " + localTimeFromMilli(start) +
            " ended at " + localTimeFromMilli(end) + ": (" + (end - start) + " ms)");
        //clean up with helper function
        removeTestFolder();
    }

    private LocalTime localTimeFromMilli(long time) {
        return Instant.ofEpochMilli(time)
            .atZone(ZoneId.systemDefault())
            .toLocalTime();
    }

    private void removeTestFolder() {
        File folder = new File(baseFolder.toFile()
            .getAbsolutePath() + testFolderName);
        folder.delete();
    }
}

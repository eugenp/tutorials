package com.baeldung.io;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.FileSystemUtils;

public class JavaDirectoryDeleteUnitTest {
    private static Path TEMP_DIRECTORY;
    private static final String DIRECTORY_NAME = "toBeDeleted";

    private static final List<String> ALL_LINES = Arrays.asList("This is line 1", "This is line 2", "This is line 3", "This is line 4", "This is line 5", "This is line 6");

    @BeforeClass
    public static void initializeTempDirectory() throws IOException {
        TEMP_DIRECTORY = Files.createTempDirectory("tmpForJUnit");
    }

    @AfterClass
    public static void cleanTempDirectory() throws IOException {
        FileUtils.deleteDirectory(TEMP_DIRECTORY.toFile());
    }

    @Before
    public void setupDirectory() throws IOException {
        Path tempPathForEachTest = Files.createDirectory(TEMP_DIRECTORY.resolve(DIRECTORY_NAME));

        // Create a directory structure
        Files.write(tempPathForEachTest.resolve("file1.txt"), ALL_LINES.subList(0, 2));
        Files.write(tempPathForEachTest.resolve("file2.txt"), ALL_LINES.subList(2, 4));

        Files.createDirectories(tempPathForEachTest.resolve("Empty"));

        Path aSubDir = Files.createDirectories(tempPathForEachTest.resolve("notEmpty"));
        Files.write(aSubDir.resolve("file3.txt"), ALL_LINES.subList(3, 5));
        Files.write(aSubDir.resolve("file4.txt"), ALL_LINES.subList(0, 3));

        aSubDir = Files.createDirectories(aSubDir.resolve("anotherSubDirectory"));
        Files.write(aSubDir.resolve("file5.txt"), ALL_LINES.subList(4, 5));
        Files.write(aSubDir.resolve("file6.txt"), ALL_LINES.subList(0, 2));
    }

    @After
    public void checkAndCleanupIfRequired() throws IOException {
        Path pathToBeDeleted = TEMP_DIRECTORY.resolve(DIRECTORY_NAME);
        if (Files.exists(pathToBeDeleted)) {
            FileUtils.deleteDirectory(pathToBeDeleted.toFile());
        }
    }

    private boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();

        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }

        return directoryToBeDeleted.delete();
    }

    @Test
    public void givenDirectory_whenDeletedWithRecursion_thenIsGone() throws IOException {
        Path pathToBeDeleted = TEMP_DIRECTORY.resolve(DIRECTORY_NAME);

        boolean result = deleteDirectory(pathToBeDeleted.toFile());

        assertTrue("Could not delete directory", result);
        assertFalse("Directory still exists", Files.exists(pathToBeDeleted));
    }

    @Test
    public void givenDirectory_whenDeletedWithCommonsIOFileUtils_thenIsGone() throws IOException {
        Path pathToBeDeleted = TEMP_DIRECTORY.resolve(DIRECTORY_NAME);

        FileUtils.deleteDirectory(pathToBeDeleted.toFile());

        assertFalse("Directory still exists", Files.exists(pathToBeDeleted));
    }

    @Test
    public void givenDirectory_whenDeletedWithSpringFileSystemUtils_thenIsGone() throws IOException {
        Path pathToBeDeleted = TEMP_DIRECTORY.resolve(DIRECTORY_NAME);

        boolean result = FileSystemUtils.deleteRecursively(pathToBeDeleted.toFile());

        assertTrue("Could not delete directory", result);
        assertFalse("Directory still exists", Files.exists(pathToBeDeleted));
    }

    @Test
    public void givenDirectory_whenDeletedWithFilesWalk_thenIsGone() throws IOException {
        Path pathToBeDeleted = TEMP_DIRECTORY.resolve(DIRECTORY_NAME);

        Files.walk(pathToBeDeleted).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);

        assertFalse("Directory still exists", Files.exists(pathToBeDeleted));
    }

    @Test
    public void givenDirectory_whenDeletedWithNIO2WalkFileTree_thenIsGone() throws IOException {
        Path pathToBeDeleted = TEMP_DIRECTORY.resolve(DIRECTORY_NAME);

        Files.walkFileTree(pathToBeDeleted, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
        });

        assertFalse("Directory still exists", Files.exists(pathToBeDeleted));
    }
}

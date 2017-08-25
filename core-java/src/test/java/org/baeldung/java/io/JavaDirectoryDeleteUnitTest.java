package org.baeldung.java.io;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.FileSystemUtils;

public class JavaDirectoryDeleteUnitTest {
    private static final Path TEMP_DIRECTORY = Paths.get("target/temp/");
    private static final String DIRECTORY_TREE_LOCATION = "src/test/resources";
    private static final String DIRECTORY_NAME = "toBeDeleted";

    @BeforeClass
    public static void initializeTemplDirectory() throws IOException {
        Files.createDirectories(TEMP_DIRECTORY);
    }

    @AfterClass
    public static void cleanTemplDirectory() throws IOException {
        FileUtils.deleteDirectory(TEMP_DIRECTORY.toFile());
    }

    private Path setupDirectory() throws IOException {
        Path tempPathForEachTest = Files.createDirectory(TEMP_DIRECTORY.resolve(UUID.randomUUID()
            .toString()))
            .resolve(DIRECTORY_NAME);

        FileUtils.copyDirectory(Paths.get(DIRECTORY_TREE_LOCATION, DIRECTORY_NAME)
            .toFile(), tempPathForEachTest.toFile());

        return tempPathForEachTest;
    }

    private void checkAndCleanupIfRequired(Path pathToBeDeleted) throws IOException {
        if (Files.exists(pathToBeDeleted)) {
            FileUtils.deleteDirectory(pathToBeDeleted.toFile());
            assertFalse("Directory still exists", Files.exists(pathToBeDeleted));
        }
    }

    boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();

        for (File file : allContents) {
            if (file.isDirectory()) {
                deleteDirectory(file);
            } else {
                file.delete();
            }
        }

        return directoryToBeDeleted.delete();
    }

    @Test
    public void givenDirectoryExists_whenDeleteingUsingRecursion_thenShouldDelete() throws IOException {
        Path pathToBeDeleted = setupDirectory();

        boolean result = deleteDirectory(pathToBeDeleted.toFile());

        assertTrue("Could not delete directory", result);
        checkAndCleanupIfRequired(pathToBeDeleted);
    }

    @Test
    public void givenDirectoryExists_whenDeleteingUsingCommonsIO_thenShouldDelete() throws IOException {
        Path pathToBeDeleted = setupDirectory();

        FileUtils.deleteDirectory(pathToBeDeleted.toFile());

        checkAndCleanupIfRequired(pathToBeDeleted);
    }

    @Test
    public void givenDirectoryExists_whenDeleteingUsingSpring_thenShouldDelete() throws IOException {
        Path pathToBeDeleted = setupDirectory();

        boolean result = FileSystemUtils.deleteRecursively(pathToBeDeleted.toFile());

        assertTrue("Could not delete directory", result);

        checkAndCleanupIfRequired(pathToBeDeleted);
    }

    @Test
    public void givenDirectoryExists_whenDeleteingUsingJava8FilesWalk_thenShouldDelete() throws IOException {
        Path pathToBeDeleted = setupDirectory();

        Files.walk(pathToBeDeleted)
            .sorted(Comparator.reverseOrder())
            .map(Path::toFile)
            .forEach(File::delete);

        checkAndCleanupIfRequired(pathToBeDeleted);
    }

    @Test
    public void givenDirectoryExists_whenDeleteingUsingNIO2WalkFileTree_thenShouldDelete() throws IOException {
        Path pathToBeDeleted = setupDirectory();

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

        checkAndCleanupIfRequired(pathToBeDeleted);
    }
}

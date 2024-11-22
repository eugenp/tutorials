package com.baeldung.size;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.StreamSupport;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class JavaFolderSizeUnitTest {
    private final long EXPECTED_SIZE = 24;
    private String path;

    @Before
    public void init() {
        final String separator = File.separator;
        path = String.format("src%stest%sresources%ssize", separator, separator, separator);
    }

    @Test
    public void whenGetFolderSizeRecursive_thenCorrect() {
        final File folder = new File(path);
        final long size = getFolderSize(folder);

        assertEquals(EXPECTED_SIZE, size);
    }

    @Test
    public void whenGetFolderSizeUsingJava7_thenCorrect() throws IOException {
        final AtomicLong size = new AtomicLong(0);
        final Path folder = Paths.get(path);

        Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                size.addAndGet(attrs.size());
                return FileVisitResult.CONTINUE;
            }
        });

        assertEquals(EXPECTED_SIZE, size.longValue());
    }

    @Test
    public void whenGetFolderSizeUsingJava8_thenCorrect() throws IOException {
        final Path folder = Paths.get(path);
        final long size = Files.walk(folder).filter(p -> p.toFile().isFile()).mapToLong(p -> p.toFile().length()).sum();

        assertEquals(EXPECTED_SIZE, size);
    }

    @Test
    public void whenGetFolderSizeUsingApacheCommonsIO_thenCorrect() {
        final File folder = new File(path);
        final long size = FileUtils.sizeOfDirectory(folder);

        assertEquals(EXPECTED_SIZE, size);
    }

    @Test
    public void whenGetFolderSizeUsingGuava_thenCorrect() {
        final File folder = new File(path);

        final Iterable<File> files = com.google.common.io.Files.fileTraverser().breadthFirst(folder);
        final long size = StreamSupport.stream(files.spliterator(), false).filter(File::isFile).mapToLong(File::length).sum();

        assertEquals(EXPECTED_SIZE, size);
    }

    @Test
    public void whenGetReadableSize_thenCorrect() {
        final File folder = new File(path);
        final long size = getFolderSize(folder);

        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        final int unitIndex = (int) (Math.log10(size) / 3);
        final double unitValue = 1 << (unitIndex * 10);

        final String readableSize = new DecimalFormat("#,##0.#").format(size / unitValue) + " " + units[unitIndex];
        assertEquals(EXPECTED_SIZE + " B", readableSize);
    }

    private long getFolderSize(final File folder) {
        long length = 0;
        final File[] files = folder.listFiles();

        for (final File file : files) {
            if (file.isFile()) {
                length += file.length();
            } else {
                length += getFolderSize(file);
            }
        }
        return length;
    }
}
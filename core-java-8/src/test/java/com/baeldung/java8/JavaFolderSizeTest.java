package com.baeldung.java8;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

public class JavaFolderSizeTest {

    private String path;

    @Before
    public void init() {
        final String separator = File.separator;
        path = "src" + separator + "test" + separator + "resources";
    }

    @Test
    public void whenGetFolderSizeRecursive_thenCorrect() {
        final long expectedSize = 136;

        final File folder = new File(path);
        final long size = getFolderSize(folder);

        assertEquals(expectedSize, size);
    }

    @Test
    public void whenGetFolderSizeUsingJava7_thenCorrect() throws IOException {
        final long expectedSize = 136;

        final AtomicLong size = new AtomicLong(0);
        final Path folder = Paths.get(path);

        Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                size.addAndGet(attrs.size());
                return FileVisitResult.CONTINUE;
            }
        });

        assertEquals(expectedSize, size.longValue());
    }

    @Test
    public void whenGetFolderSizeUsingJava8_thenCorrect() throws IOException {
        final long expectedSize = 136;

        final Path folder = Paths.get(path);
        final long size = Files.walk(folder).filter(p -> p.toFile().isFile()).mapToLong(p -> p.toFile().length()).sum();

        assertEquals(expectedSize, size);
    }

    @Test
    public void whenGetFolderSizeUsingApacheCommonsIO_thenCorrect() {
        final long expectedSize = 136;

        final File folder = new File(path);
        final long size = FileUtils.sizeOfDirectory(folder);

        assertEquals(expectedSize, size);
    }

    @Test
    public void whenGetFolderSizeUsingGuava_thenCorrect() {
        final long expectedSize = 136;

        final File folder = new File(path);

        final Iterable<File> files = com.google.common.io.Files.fileTreeTraverser().breadthFirstTraversal(folder);
        final long size = StreamSupport.stream(files.spliterator(), false).filter(f -> f.isFile()).mapToLong(File::length).sum();

        assertEquals(expectedSize, size);
    }

    @Test
    public void whenGetReadableSize_thenCorrect() {
        final File folder = new File(path);
        final long size = getFolderSize(folder);

        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        final int unitIndex = (int) (Math.log10(size) / 3);
        final double unitValue = 1 << (unitIndex * 10);

        final String readableSize = new DecimalFormat("#,##0.#").format(size / unitValue) + " " + units[unitIndex];
        assertEquals("136 B", readableSize);
    }

    private long getFolderSize(final File folder) {
        long length = 0;
        final File[] files = folder.listFiles();

        final int count = files.length;

        for (int i = 0; i < count; i++) {
            if (files[i].isFile()) {
                length += files[i].length();
            } else {
                length += getFolderSize(files[i]);
            }
        }
        return length;
    }
}
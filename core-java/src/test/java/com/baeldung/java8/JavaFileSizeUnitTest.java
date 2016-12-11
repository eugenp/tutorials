package com.baeldung.java8;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class JavaFileSizeUnitTest {
    private final long EXPECTED_SIZE = 11;
    private String path;

    @Before
    public void init() {
        final String separator = File.separator;
        path = String.format("src%stest%sresources%stestFolder%ssample_file_1.in", separator, separator, separator, separator);
    }

    @Test
    public void whenGetFileSize_thenCorrect() {
        final File file = new File(path);
        
        final long size = getFileSize(file);

        assertEquals(EXPECTED_SIZE, size);
    }

    @Test
    public void whenGetFileSizeUsingNioApi_thenCorrect() throws IOException {
        final Path path = Paths.get(this.path);
        final FileChannel fileChannel = FileChannel.open(path);
        
        final long fileSize = fileChannel.size();
        
        assertEquals(EXPECTED_SIZE, fileSize);
    }

    @Test
    public void whenGetFileSizeUsingApacheCommonsIO_thenCorrect() {
        final File file = new File(path);
        
        final long size = FileUtils.sizeOf(file);

        assertEquals(EXPECTED_SIZE, size);
    }

    @Test
    public void whenGetReadableFileSize_thenCorrect() {
        final File file = new File(path);
        
        final long size = getFileSize(file);
        
        assertEquals(EXPECTED_SIZE + " bytes", FileUtils.byteCountToDisplaySize(size));
    }

    private long getFileSize(final File file) {
        final long length = file.length();
        return length;
    }
}
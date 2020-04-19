package com.baeldung.size;

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
    private static final long EXPECTED_FILE_SIZE_IN_BYTES = 11;
    private String filePath;

    @Before
    public void init() {
        final String separator = File.separator;
        filePath = String.join(separator, new String[] { "src", "test", "resources", "size", "sample_file_1.in" });
    }

    @Test
    public void whenGetFileSize_thenCorrect() {
        final File file = new File(filePath);

        final long size = getFileSize(file);

        assertEquals(EXPECTED_FILE_SIZE_IN_BYTES, size);
    }

    @Test
    public void whenGetFileSizeUsingNioApi_thenCorrect() throws IOException {
        final Path path = Paths.get(this.filePath);
        final FileChannel fileChannel = FileChannel.open(path);

        final long fileSize = fileChannel.size();

        assertEquals(EXPECTED_FILE_SIZE_IN_BYTES, fileSize);
    }

    @Test
    public void whenGetFileSizeUsingApacheCommonsIO_thenCorrect() {
        final File file = new File(filePath);

        final long size = FileUtils.sizeOf(file);

        assertEquals(EXPECTED_FILE_SIZE_IN_BYTES, size);
    }

    @Test
    public void whenGetReadableFileSize_thenCorrect() {
        final File file = new File(filePath);

        final long size = getFileSize(file);

        assertEquals(EXPECTED_FILE_SIZE_IN_BYTES + " bytes", FileUtils.byteCountToDisplaySize(size));
    }

    private long getFileSize(final File file) {
        final long length = file.length();
        return length;
    }
}
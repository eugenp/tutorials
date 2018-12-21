package com.baeldung.filechannel;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.NonWritableChannelException;
import java.nio.file.Paths;

import static com.baeldung.filechannel.FileChannelIO.verifiedRead;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;
import static org.junit.Assert.assertEquals;

public class FileChannelWriteUnitTest {
    private static final String WRITE_RESOURCE = "src/test/resources/filechannel/write.txt";

    @Test
    public void givenFileChannel_whenWrite_thenCorrect() throws IOException {
        try (FileChannelIO io = new FileChannelIO(FileChannel.open(Paths.get(WRITE_RESOURCE), WRITE))) {

            io.write("baeldung.com");

            assertEquals("baeldung.com", verifiedRead(WRITE_RESOURCE));
        }
    }

    @Test(expected = NonWritableChannelException.class)
    public void givenReadableFileChannel_whenWrite_thenException() throws IOException {
        try (FileChannelIO io = new FileChannelIO(FileChannel.open(Paths.get(WRITE_RESOURCE), READ))) {

            io.write("baeldung.com");
        }
    }

    @Test
    public void givenFileOutputStream_whenWrite_thenCorrect() throws IOException {
        try (FileChannelIO io = new FileChannelIO(new FileOutputStream(WRITE_RESOURCE).getChannel())) {

            io.write("baeldung.com");

            assertEquals("baeldung.com", verifiedRead(WRITE_RESOURCE));
        }
    }

    @Test(expected = NonWritableChannelException.class)
    public void givenFileInputStream_whenWrite_thenException() throws IOException {
        try (FileChannelIO io = new FileChannelIO(new FileInputStream(WRITE_RESOURCE).getChannel())) {

            io.write("baeldung.com");
        }
    }

    @Test
    public void givenRandomAccessFile_whenWrite_thenCorrect() throws IOException {
        try (FileChannelIO io = new FileChannelIO(new RandomAccessFile(WRITE_RESOURCE, "rw").getChannel())) {

            io.write("baeldung.com");

            assertEquals("baeldung.com", verifiedRead(WRITE_RESOURCE));
        }
    }

    @Test(expected = NonWritableChannelException.class)
    public void givenRandomAccessFileInReadMode_whenWrite_thenException() throws IOException {
        try (FileChannelIO io = new FileChannelIO(new RandomAccessFile(WRITE_RESOURCE, "r").getChannel())) {

            io.write("baeldung.com");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenRandomAccessFileInWriteMode_whenRead_thenException() throws IOException {
        new RandomAccessFile(WRITE_RESOURCE, "w").getChannel();
    }
}
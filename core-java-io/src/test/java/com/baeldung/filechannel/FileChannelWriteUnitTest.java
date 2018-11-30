package com.baeldung.filechannel;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.nio.channels.NonWritableChannelException;
import java.nio.file.Paths;

import static com.baeldung.filechannel.FileChannelIO.uri;
import static com.baeldung.filechannel.FileChannelIO.verifiedRead;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;
import static org.junit.Assert.assertEquals;

public class FileChannelWriteUnitTest {
    private static final String WRITE_RESOURCE = "com/baeldung/filechannel/write.txt";
    private final URI file = uri(WRITE_RESOURCE);

    @Test
    public void givenFileChannel_whenWrite_thenCorrect() throws IOException {
        try (FileChannelIO io = new FileChannelIO(FileChannel.open(Paths.get(file), WRITE), "baeldung.com")) {

            io.write();

            assertEquals("baeldung.com", verifiedRead(file));
        }
    }

    @Test(expected = NonWritableChannelException.class)
    public void givenReadableFileChannel_whenWrite_thenException() throws IOException {
        try (FileChannelIO io = new FileChannelIO(FileChannel.open(Paths.get(file), READ), "baeldung.com")) {

            io.write();
        }
    }

    @Test
    public void givenFileOutputStream_whenWrite_thenCorrect() throws IOException {
        try (FileChannelIO io = new FileChannelIO(new FileOutputStream(file.getPath()).getChannel(), "baeldung.com")) {

            io.write();

            assertEquals("baeldung.com", verifiedRead(file));
        }
    }

    @Test(expected = NonWritableChannelException.class)
    public void givenFileInputStream_whenWrite_thenException() throws IOException {
        try (FileChannelIO io = new FileChannelIO(new FileInputStream(file.getPath()).getChannel(), "baeldung.com")) {

            io.write();
        }
    }

    @Test
    public void givenRandomAccessFile_whenWrite_thenCorrect() throws IOException {
        try (FileChannelIO io = new FileChannelIO(new RandomAccessFile(file.getPath(), "rw").getChannel(), "baeldung.com")) {

            io.write();

            assertEquals("baeldung.com", verifiedRead(file));
        }
    }

    @Test(expected = NonWritableChannelException.class)
    public void givenRandomAccessFileInReadMode_whenWrite_thenException() throws IOException {
        try (FileChannelIO io = new FileChannelIO(new RandomAccessFile(file.getPath(), "r").getChannel(), "baeldung.com")) {

            io.write();
        }
    }
}
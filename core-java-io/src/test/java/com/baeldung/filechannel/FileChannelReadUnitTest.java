package com.baeldung.filechannel;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.nio.channels.NonReadableChannelException;
import java.nio.file.Paths;

import static com.baeldung.filechannel.FileChannelIO.uri;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;
import static org.junit.Assert.assertEquals;

public class FileChannelReadUnitTest {
    private static final String READ_RESOURCE = "com/baeldung/filechannel/read.txt";
    private final URI file = uri(READ_RESOURCE);

    @Test
    public void givenFileChannel_whenRead_thenCorrect() throws IOException {
        try (FileChannelIO io = new FileChannelIO(FileChannel.open(Paths.get(file), READ))) {

            io.read();

            assertEquals("baeldung.com", io.content());
        }
    }

    @Test(expected = NonReadableChannelException.class)
    public void givenFileChannelInWriteMode_whenRead_thenException() throws IOException {
        try (FileChannelIO io = new FileChannelIO(FileChannel.open(Paths.get(file), WRITE))) {

            io.read();
        }
    }

    @Test
    public void givenFileInputStream_whenRead_thenCorrect() throws IOException {
        try (FileChannelIO io = new FileChannelIO(new FileInputStream(file.getPath()).getChannel())) {

            io.read();

            assertEquals("baeldung.com", io.content());
        }
    }

    @Test
    public void givenFileOutputStreamInAppendMode_whenChangePosition_thenNoEffect() throws IOException {
        try (FileChannel channel = new FileOutputStream(file.getPath(), true).getChannel()) {

            assertEquals(12, channel.position());
            assertEquals(12, channel.position(0).position());
        }
    }

    @Test
    public void givenRandomAccessFile_whenRead_thenCorrect() throws IOException {
        try (FileChannelIO io = new FileChannelIO(new RandomAccessFile(file.getPath(), "r").getChannel())) {

            io.read();

            assertEquals("baeldung.com", io.content());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenRandomAccessFileInWriteMode_whenRead_thenException() throws IOException {
        try (FileChannelIO io = new FileChannelIO(new RandomAccessFile(file.getPath(), "w").getChannel())) {
        }
    }
}
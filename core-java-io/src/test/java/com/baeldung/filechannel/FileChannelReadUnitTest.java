package com.baeldung.filechannel;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.NonReadableChannelException;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;
import static org.junit.Assert.assertEquals;

public class FileChannelReadUnitTest {
    private static final String READ_RESOURCE = "src/test/resources/filechannel/read.txt";

    @Test
    public void givenFileChannel_whenRead_thenCorrect() throws IOException {
        try (FileChannelIO io = new FileChannelIO(FileChannel.open(Paths.get(READ_RESOURCE), READ))) {

            assertEquals("baeldung.com", io.read());
        }
    }

    @Test(expected = NonReadableChannelException.class)
    public void givenFileChannelInWriteMode_whenRead_thenException() throws IOException {
        try (FileChannelIO io = new FileChannelIO(FileChannel.open(Paths.get(READ_RESOURCE), WRITE))) {

            io.read();
        }
    }

    @Test
    public void givenFileInputStream_whenRead_thenCorrect() throws IOException {
        try (FileChannelIO io = new FileChannelIO(new FileInputStream(READ_RESOURCE).getChannel())) {

            assertEquals("baeldung.com", io.read());
        }
    }

    @Test
    public void givenFileOutputStreamInAppendMode_whenChangePosition_thenNoEffect() throws IOException {
        try (FileChannel channel = new FileOutputStream(READ_RESOURCE, true).getChannel()) {

            assertEquals(12, channel.position());
            assertEquals(12, channel.position(0).position());
        }
    }

    @Test
    public void givenRandomAccessFile_whenRead_thenCorrect() throws IOException {
        try (FileChannelIO io = new FileChannelIO(new RandomAccessFile(READ_RESOURCE, "r").getChannel())) {

            assertEquals("baeldung.com", io.read());
        }
    }
}
package com.baeldung.filechannel;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;

import static com.baeldung.filechannel.FileChannelIO.uri;
import static com.baeldung.filechannel.FileChannelIO.verifiedRead;
import static java.lang.String.format;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FileChannelTransferUnitTest {
    private static final String READ_RESOURCE = "com/baeldung/filechannel/readTransfer.txt";
    private static final String WRITE_RESOURCE = "com/baeldung/filechannel/writeTransfer%d.txt";
    private final URI readFile = uri(READ_RESOURCE);

    @Test
    public void givenFileChannels_whenTransferFrom_thenCorrect() throws IOException {
        URI writeFile = uri(format(WRITE_RESOURCE, 1));
        try (FileChannel readableChannel = FileChannel.open(Paths.get(readFile), READ);
             FileChannel writableChannel = FileChannel.open(Paths.get(writeFile), WRITE)) {

            writableChannel.transferFrom(readableChannel, writableChannel.position(), readableChannel.size());

            assertEquals("baeldung.com", verifiedRead(writeFile));
        }
    }

    @Test
    public void givenFileChannels_whenTransferFromNonZeroPosition_thenNull() throws IOException {
        URI writeFile = uri(format(WRITE_RESOURCE, 2));
        try (FileChannel readableChannel = FileChannel.open(Paths.get(readFile), READ);
             FileChannel writableChannel = FileChannel.open(Paths.get(writeFile), WRITE)) {

            writableChannel.transferFrom(readableChannel, 9, 3);

            assertNull(verifiedRead(writeFile));
        }
    }

    @Test
    public void givenFileChannels_whenTransferToNonZeroPosition_thenCorrect() throws IOException {
        URI writeFile = uri(format(WRITE_RESOURCE, 3));
        try (FileChannel readableChannel = FileChannel.open(Paths.get(readFile), READ);
             FileChannel writableChannel = FileChannel.open(Paths.get(writeFile), WRITE)) {

            readableChannel.transferTo(9, 3, writableChannel);

            assertEquals("com", verifiedRead(writeFile));
        }
    }

    @Test
    public void givenFileInputOutputStreams_whenTransferFrom_thenCorrect() throws IOException {
        URI writeFile = uri(format(WRITE_RESOURCE, 4));
        try (FileChannel readableChannel = new FileInputStream(readFile.getPath()).getChannel();
             FileChannel writableChannel = new FileOutputStream(writeFile.getPath()).getChannel()) {

            writableChannel.transferFrom(readableChannel, writableChannel.position(), readableChannel.size());

            assertEquals("baeldung.com", verifiedRead(writeFile));
        }
    }

    @Test
    public void whenTransferToRunWithRandomAccessFile_thenCorrect() throws IOException {
        URI writeFile = uri(format(WRITE_RESOURCE, 5));
        try (FileChannel readableChannel = new RandomAccessFile(readFile.getPath(), "r").getChannel();
             FileChannel writableChannel = new RandomAccessFile(writeFile.getPath(), "rw").getChannel()) {

            readableChannel.transferTo(readableChannel.position(), readableChannel.size(), writableChannel);

            assertEquals("baeldung.com", verifiedRead(writeFile));
        }
    }
}
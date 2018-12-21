package com.baeldung.filechannel;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.baeldung.filechannel.FileChannelIO.clearContent;
import static com.baeldung.filechannel.FileChannelIO.verifiedRead;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FileChannelTransferUnitTest {
    private static final String READ_RESOURCE = "src/test/resources/filechannel/readTransfer.txt";
    private static final String WRITE_RESOURCE = "src/test/resources/filechannel/writeTransfer.txt";

    @Before
    public void setup() throws IOException {
        clearContent(WRITE_RESOURCE);
    }

    @Test
    public void givenFileChannels_whenTransferFrom_thenCorrect() throws IOException {
        try (FileChannel readableChannel = FileChannel.open(Paths.get(READ_RESOURCE), READ);
             FileChannel writableChannel = FileChannel.open(Paths.get(WRITE_RESOURCE), WRITE)) {

            writableChannel.transferFrom(readableChannel, writableChannel.position(), readableChannel.size());

            assertEquals("baeldung.com", verifiedRead(WRITE_RESOURCE));
        }
    }

    @Test
    public void givenFileChannels_whenTransferFromNonZeroPositionAndToEmptyFile_thenNull() throws IOException {
        try (FileChannel readableChannel = FileChannel.open(Paths.get(READ_RESOURCE), READ);
             FileChannel writableChannel = FileChannel.open(Paths.get(WRITE_RESOURCE), WRITE)) {

            writableChannel.transferFrom(readableChannel, 9, 3);

            assertNull(verifiedRead(WRITE_RESOURCE));
        }
    }

    @Test
    public void givenFileChannels_whenTransferToNonZeroPositionAndFromNonEmptyFile_thenCorrect() throws IOException {
        try (FileChannel readableChannel = FileChannel.open(Paths.get(READ_RESOURCE), READ);
             FileChannel writableChannel = FileChannel.open(Paths.get(WRITE_RESOURCE), WRITE)) {

            readableChannel.transferTo(9, 3, writableChannel);

            assertEquals("com", verifiedRead(WRITE_RESOURCE));
        }
    }

    @Test
    public void givenFileInputOutputStreams_whenTransferFrom_thenCorrect() throws IOException {
        try (FileChannel readableChannel = new FileInputStream(READ_RESOURCE).getChannel();
             FileChannel writableChannel = new FileOutputStream(WRITE_RESOURCE).getChannel()) {

            writableChannel.transferFrom(readableChannel, writableChannel.position(), readableChannel.size());

            assertEquals("baeldung.com", verifiedRead(WRITE_RESOURCE));
        }
    }

    @Test
    public void whenTransferToRunWithRandomAccessFile_thenCorrect() throws IOException {
        try (FileChannel readableChannel = new RandomAccessFile(READ_RESOURCE, "r").getChannel();
             FileChannel writableChannel = new RandomAccessFile(WRITE_RESOURCE, "rw").getChannel()) {

            readableChannel.transferTo(readableChannel.position(), readableChannel.size(), writableChannel);

            assertEquals("baeldung.com", verifiedRead(WRITE_RESOURCE));
        }
    }
}
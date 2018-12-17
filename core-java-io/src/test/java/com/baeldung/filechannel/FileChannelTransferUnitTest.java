package com.baeldung.filechannel;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;

import static com.baeldung.filechannel.FileChannelIO.clearContent;
import static com.baeldung.filechannel.FileChannelIO.uri;
import static com.baeldung.filechannel.FileChannelIO.verifiedRead;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FileChannelTransferUnitTest {
    private static final String READ_RESOURCE = "filechannel/readTransfer.txt";
    private static final String WRITE_RESOURCE = "filechannel/writeTransfer.txt";
    private final URI readFile = uri(READ_RESOURCE);
    private final URI writeFile = uri(WRITE_RESOURCE);

    @Before
    public void setup() throws IOException {
        clearContent(writeFile);
    }

    @Test
    public void givenFileChannels_whenTransferFrom_thenCorrect() throws IOException {
        try (FileChannel readableChannel = FileChannel.open(Paths.get(readFile), READ);
             FileChannel writableChannel = FileChannel.open(Paths.get(writeFile), WRITE)) {

            writableChannel.transferFrom(readableChannel, writableChannel.position(), readableChannel.size());

            assertEquals("baeldung.com", verifiedRead(writeFile));
        }
    }

    @Test
    public void givenFileChannels_whenTransferFromNonZeroPositionAndToEmptyFile_thenNull() throws IOException {
        try (FileChannel readableChannel = FileChannel.open(Paths.get(readFile), READ);
             FileChannel writableChannel = FileChannel.open(Paths.get(writeFile), WRITE)) {

            writableChannel.transferFrom(readableChannel, 9, 3);

            assertNull(verifiedRead(writeFile));
        }
    }

    @Test
    public void givenFileChannels_whenTransferToNonZeroPositionAndFromNonEmptyFile_thenCorrect() throws IOException {
        try (FileChannel readableChannel = FileChannel.open(Paths.get(readFile), READ);
             FileChannel writableChannel = FileChannel.open(Paths.get(writeFile), WRITE)) {

            readableChannel.transferTo(9, 3, writableChannel);

            assertEquals("com", verifiedRead(writeFile));
        }
    }

    @Test
    public void givenFileInputOutputStreams_whenTransferFrom_thenCorrect() throws IOException {
        try (FileChannel readableChannel = new FileInputStream(readFile.getPath()).getChannel();
             FileChannel writableChannel = new FileOutputStream(writeFile.getPath()).getChannel()) {

            writableChannel.transferFrom(readableChannel, writableChannel.position(), readableChannel.size());

            assertEquals("baeldung.com", verifiedRead(writeFile));
        }
    }

    @Test
    public void whenTransferToRunWithRandomAccessFile_thenCorrect() throws IOException {
        try (FileChannel readableChannel = new RandomAccessFile(readFile.getPath(), "r").getChannel();
             FileChannel writableChannel = new RandomAccessFile(writeFile.getPath(), "rw").getChannel()) {

            readableChannel.transferTo(readableChannel.position(), readableChannel.size(), writableChannel);

            assertEquals("baeldung.com", verifiedRead(writeFile));
        }
    }
}
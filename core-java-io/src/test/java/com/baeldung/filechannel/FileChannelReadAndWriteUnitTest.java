package com.baeldung.filechannel;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;

import static com.baeldung.filechannel.FileChannelIO.uri;
import static com.baeldung.filechannel.FileChannelIO.verifiedRead;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;
import static org.junit.Assert.assertEquals;

public class FileChannelReadAndWriteUnitTest {
    private static final String READ_WRITE_RESOURCE = "filechannel/readAndWrite.txt";
    private final URI file = uri(READ_WRITE_RESOURCE);

    @Test
    public void givenFileChannel_whenReadAndWrite_thenCorrect() throws IOException {
        String readBefore = verifiedRead(file);
        try (FileChannelIO io = new FileChannelIO(FileChannel.open(Paths.get(file), READ, WRITE))) {

            String content = io.read();
            io.write(content);

            assertEquals(readBefore + readBefore, verifiedRead(file));
        }
    }

    @Test
    public void givenRandomAccessFile_whenReadAndWrite_thenCorrect() throws IOException {
        String readBefore = verifiedRead(file);
        try (FileChannelIO io = new FileChannelIO(new RandomAccessFile(file.getPath(), "rw").getChannel())) {

            String content = io.read();
            io.write(content);

            assertEquals(readBefore + readBefore, verifiedRead(file));
        }
    }
}
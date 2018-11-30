package com.baeldung.filechannel;

import org.junit.Test;

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

public class FileChannelReadAndWriteUnitTest {
    private static final String READ_WRITE_RESOURCE = "com/baeldung/filechannel/readThenWrite%d.txt";

    @Test
    public void givenFileChannel_whenReadAndWrite_thenCorrect() throws IOException {
        URI file = uri(format(READ_WRITE_RESOURCE, 1));
        String readBefore = verifiedRead(file);
        try (FileChannelIO io = new FileChannelIO(FileChannel.open(Paths.get(file), READ, WRITE))) {

            io.read();
            io.write();

            assertEquals(readBefore + readBefore, verifiedRead(file));
        }
    }

    @Test
    public void givenRandomAccessFile_whenReadAndWrite_thenCorrect() throws IOException {
        URI file = uri(format(READ_WRITE_RESOURCE, 2));
        String readBefore = verifiedRead(file);
        try (FileChannelIO io = new FileChannelIO(new RandomAccessFile(file.getPath(), "rw").getChannel())) {

            io.read();
            io.write();

            assertEquals(readBefore + readBefore, verifiedRead(file));
        }
    }
}
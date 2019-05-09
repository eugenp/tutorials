package com.baeldung.filechannel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class FileChannelUnitTest {

    @Test
    public void givenFile_whenReadWithFileChannelUsingRandomAccessFile_thenCorrect() throws IOException {

        try (RandomAccessFile reader = new RandomAccessFile("src/test/resources/test_read.in", "r"); 
            FileChannel channel = reader.getChannel(); 
            ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            int bufferSize = 1024;
            if (bufferSize > channel.size()) {
                bufferSize = (int) channel.size();
            }
            ByteBuffer buff = ByteBuffer.allocate(bufferSize);

            while (channel.read(buff) > 0) {
                out.write(buff.array(), 0, buff.position());
                buff.clear();
            }

            String fileContent = new String(out.toByteArray(), StandardCharsets.UTF_8);

            assertEquals("Hello world", fileContent);
        }
    }

    @Test
    public void givenFile_whenReadWithFileChannelUsingFileInputStream_thenCorrect() throws IOException {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream(); 
            FileInputStream fin = new FileInputStream("src/test/resources/test_read.in"); 
            FileChannel channel = fin.getChannel()) {

            int bufferSize = 1024;
            if (bufferSize > channel.size()) {
                bufferSize = (int) channel.size();
            }
            ByteBuffer buff = ByteBuffer.allocate(bufferSize);

            while (channel.read(buff) > 0) {
                out.write(buff.array(), 0, buff.position());
                buff.clear();
            }
            String fileContent = new String(out.toByteArray(), StandardCharsets.UTF_8);

            assertEquals("Hello world", fileContent);
        }
    }

    @Test
    public void givenFile_whenReadAFileSectionIntoMemoryWithFileChannel_thenCorrect() throws IOException {

        try (RandomAccessFile reader = new RandomAccessFile("src/test/resources/test_read.in", "r"); 
            FileChannel channel = reader.getChannel(); 
            ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            MappedByteBuffer buff = channel.map(FileChannel.MapMode.READ_ONLY, 6, 5);

            if (buff.hasRemaining()) {
                byte[] data = new byte[buff.remaining()];
                buff.get(data);
                assertEquals("world", new String(data, StandardCharsets.UTF_8));
            }
        }
    }

    @Test
    public void whenWriteWithFileChannelUsingRandomAccessFile_thenCorrect() throws IOException {
        String file = "src/test/resources/test_write_using_filechannel.txt";
        try (RandomAccessFile writer = new RandomAccessFile(file, "rw"); 
            FileChannel channel = writer.getChannel()) {
            ByteBuffer buff = ByteBuffer.wrap("Hello world".getBytes(StandardCharsets.UTF_8));

            channel.write(buff);

            // now we verify whether the file was written correctly
            RandomAccessFile reader = new RandomAccessFile(file, "r");
            assertEquals("Hello world", reader.readLine());
            reader.close();
        }
    }

    @Test
    public void givenFile_whenWriteAFileUsingLockAFileSectionWithFileChannel_thenCorrect() throws IOException {
        try (RandomAccessFile reader = new RandomAccessFile("src/test/resources/test_read.in", "rw"); 
            FileChannel channel = reader.getChannel(); 
            FileLock fileLock = channel.tryLock(6, 5, Boolean.FALSE);) {

            assertNotNull(fileLock);
        }
    }

    @Test
    public void givenFile_whenReadWithFileChannelGetPosition_thenCorrect() throws IOException {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream(); 
            RandomAccessFile reader = new RandomAccessFile("src/test/resources/test_read.in", "r"); 
            FileChannel channel = reader.getChannel()) {

            int bufferSize = 1024;
            if (bufferSize > channel.size()) {
                bufferSize = (int) channel.size();
            }
            ByteBuffer buff = ByteBuffer.allocate(bufferSize);

            while (channel.read(buff) > 0) {
                out.write(buff.array(), 0, buff.position());
                buff.clear();
            }

            // the original file is 11 bytes long, so that's where the position pointer should be
            assertEquals(11, channel.position());

            channel.position(4);
            assertEquals(4, channel.position());
        }
    }

    @Test
    public void whenGetFileSize_thenCorrect() throws IOException {

        try (RandomAccessFile reader = new RandomAccessFile("src/test/resources/test_read.in", "r"); 
            FileChannel channel = reader.getChannel()) {

            // the original file is 11 bytes long, so that's where the position pointer should be
            assertEquals(11, channel.size());
        }
    }

    @Test
    public void whenTruncateFile_thenCorrect() throws IOException {
        String input = "this is a test input";

        FileOutputStream fout = new FileOutputStream("src/test/resources/test_truncate.txt");
        FileChannel channel = fout.getChannel();

        ByteBuffer buff = ByteBuffer.wrap(input.getBytes());
        channel.write(buff);
        buff.flip();

        channel = channel.truncate(5);
        assertEquals(5, channel.size());

        fout.close();
        channel.close();
    }
}

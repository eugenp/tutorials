package com.baeldung.filechannel;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

public class FileChannelUnitTest {
    @Test
    public void givenFile_whenReadWithFileChannelUsingRandomAccessFile_thenCorrect() throws IOException {
        String expected_value = "Hello world";
        String file = "src/test/resources/test_read.in";
        RandomAccessFile reader = new RandomAccessFile(file, "r");
        FileChannel channel = reader.getChannel();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int bufferSize = 1024;
        if (bufferSize > channel.size()) {
            bufferSize = (int) channel.size();
        }
        ByteBuffer buff = ByteBuffer.allocate(bufferSize);
        int noOfBytesRead = channel.read(buff);

        while (noOfBytesRead != -1) {
            if (buff.hasArray()) {
                out.write(buff.array(), 0, buff.position());
                buff.clear();
                noOfBytesRead = channel.read(buff);
            }
        }

        assertEquals(expected_value, out.toString());
        out.close();
        channel.close();
        reader.close();
    }

    @Test
    public void givenFile_whenReadWithFileChannelUsingFileInputStream_thenCorrect() throws IOException {
        String expected_value = "Hello world";
        String file = "src/test/resources/test_read.in";
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        FileInputStream fin = new FileInputStream(file);
        FileChannel channel = fin.getChannel();

        int bufferSize = 1024;
        if (bufferSize > channel.size()) {
            bufferSize = (int) channel.size();
        }
        ByteBuffer buff = ByteBuffer.allocate(bufferSize);
        int noOfBytesRead = channel.read(buff);

        while (noOfBytesRead != -1) {
            if (buff.hasArray()) {
                out.write(buff.array(), 0, buff.position());
                buff.clear();
                noOfBytesRead = channel.read(buff);
            }
        }

        assertEquals(expected_value, out.toString());
        channel.close();
        fin.close();
    }

    @Test
    public void whenWriteWithFileChannelUsingRandomAccessFile_thenCorrect() throws IOException {
        String input = "Hello world";
        String file = "src/test/resources/test_write_using_filechannel.txt";

        RandomAccessFile writer = new RandomAccessFile(file, "rw");
        FileChannel channel = writer.getChannel();

        ByteBuffer buff = ByteBuffer.wrap(input.getBytes());
        while (buff.hasRemaining()) {
            channel.write(buff);
        }
        channel.close();
        writer.close();

        // verify
        RandomAccessFile reader = new RandomAccessFile(file, "r");
        assertEquals(input, reader.readLine());
        reader.close();
    }

    @Test
    public void whenWriteWithFileChannelUsingFileOutputStream_thenCorrect() throws IOException {
        String input = "Hello world";
        String file = "src/test/resources/test_write_using_filechannel.txt";

        FileOutputStream fout = new FileOutputStream(file);
        FileChannel channel = fout.getChannel();

        ByteBuffer buff = ByteBuffer.wrap(input.getBytes());
        while (buff.hasRemaining()) {
            channel.write(buff);
        }

        channel.close();
        fout.close();

        // verify
        RandomAccessFile reader = new RandomAccessFile(file, "r");
        assertEquals(input, reader.readLine());
        reader.close();
    }

    @Test
    public void givenFile_whenReadWithFileChannelGetPosition_thenCorrect() throws IOException {
        long expected_value = 11;
        String file = "src/test/resources/test_read.in";
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        RandomAccessFile reader = new RandomAccessFile(file, "r");
        FileChannel channel = reader.getChannel();

        int bufferSize = 1024;
        if (bufferSize > channel.size()) {
            bufferSize = (int) channel.size();
        }
        ByteBuffer buff = ByteBuffer.allocate(bufferSize);
        int noOfBytesRead = channel.read(buff);

        while (noOfBytesRead != -1) {
            if (buff.hasArray()) {
                out.write(buff.array(), 0, buff.position());
                buff.clear();
                noOfBytesRead = channel.read(buff);
            }
        }

        assertEquals(expected_value, channel.position());

        channel.position(4);
        assertEquals(4, channel.position());

        channel.close();
        reader.close();
    }

    @Test
    public void whenGetFileSize_thenCorrect() throws IOException {
        long expectedSize = 11;
        String file = "src/test/resources/test_read.in";
        RandomAccessFile reader = new RandomAccessFile(file, "r");
        FileChannel channel = reader.getChannel();

        long imageFileSize = channel.size();
        assertEquals(expectedSize, imageFileSize);

        channel.close();
        reader.close();
    }

    @Test
    public void whenTruncateFile_thenCorrect() throws IOException {
        long expectedSize = 5;
        String input = "this is a test input";
        String file = "src/test/resources/test_truncate.txt";

        FileOutputStream fout = new FileOutputStream(file);
        FileChannel channel = fout.getChannel();

        ByteBuffer buff = ByteBuffer.wrap(input.getBytes());
        channel.write(buff);
        buff.flip();

        channel = channel.truncate(5);
        assertEquals(expectedSize, channel.size());

        fout.close();
        channel.close();
    }
}

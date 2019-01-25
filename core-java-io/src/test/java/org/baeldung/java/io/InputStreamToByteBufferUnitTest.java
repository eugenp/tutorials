package org.baeldung.java.io;

<<<<<<< HEAD

import com.google.common.io.ByteStreams;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputStreamToByteBufferUnitTest {

    @Test
    public void givenUsingCoreClasses_whenWritingAFileIntoAByteBuffer_thenBytesLengthMustMatch() throws IOException {
        File inputFile = getFile();
        ByteBuffer bufferByte = ByteBuffer.allocate((int) inputFile.length());
        FileInputStream in = new FileInputStream(inputFile);
        in.getChannel().read(bufferByte);

        assertEquals(bufferByte.position(), inputFile.length());
    }

    @Test
    public void givenUsingCommonsIo_whenWritingAFileIntoAByteBuffer_thenBytesLengthMustMatch() throws IOException {
        File inputFile = getFile();
        ByteBuffer bufferByte = ByteBuffer.allocateDirect((int) inputFile.length());
        ReadableByteChannel readableByteChannel = new FileInputStream(inputFile).getChannel();
        IOUtils.readFully(readableByteChannel, bufferByte);

        assertEquals(bufferByte.position(), inputFile.length());
    }

    @Test
    public void givenUsingGuava_whenWritingAFileIntoAByteBuffer_thenBytesLengthMustMatch() throws IOException {
        File inputFile = getFile();
        FileInputStream in = new FileInputStream(inputFile);
        byte[] targetArray = ByteStreams.toByteArray(in);
        ByteBuffer bufferByte = ByteBuffer.wrap(targetArray);
        bufferByte.rewind();
        while (bufferByte.hasRemaining()) {
            bufferByte.get();
        }
        
        assertEquals(bufferByte.position(), inputFile.length());
    }

    private File getFile() {
        ClassLoader classLoader = new InputStreamToByteBufferUnitTest().getClass().getClassLoader();

        String fileName = "frontenac-2257154_960_720.jpg";

        return new File(classLoader.getResource(fileName).getFile());
=======
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

import static java.nio.channels.Channels.newChannel;
import static org.junit.Assert.assertEquals;

public class InputStreamToByteBufferUnitTest {

    @Test
    public void givenUsingCoreClasses_whenByteArrayInputStreamToAByteBuffer_thenLengthMustMatch() throws IOException {
        byte[] input = new byte[] { 0, 1, 2 };
        InputStream initialStream = new ByteArrayInputStream(input);
        ByteBuffer byteBuffer = ByteBuffer.allocate(3);
        while (initialStream.available() > 0) {
            byteBuffer.put((byte) initialStream.read());
        }

        assertEquals(byteBuffer.position(), input.length);
    }

    @Test
    public void givenUsingGuava__whenByteArrayInputStreamToAByteBuffer_thenLengthMustMatch() throws IOException {
        InputStream initialStream = ByteSource
          .wrap(new byte[] { 0, 1, 2 })
          .openStream();
        byte[] targetArray = ByteStreams.toByteArray(initialStream);
        ByteBuffer bufferByte = ByteBuffer.wrap(targetArray);
        while (bufferByte.hasRemaining()) {
            bufferByte.get();
        }

        assertEquals(bufferByte.position(), targetArray.length);
    }

    @Test
    public void givenUsingCommonsIo_whenByteArrayInputStreamToAByteBuffer_thenLengthMustMatch() throws IOException {
        byte[] input = new byte[] { 0, 1, 2 };
        InputStream initialStream = new ByteArrayInputStream(input);
        ByteBuffer byteBuffer = ByteBuffer.allocate(3);
        ReadableByteChannel channel = newChannel(initialStream);
        IOUtils.readFully(channel, byteBuffer);

        assertEquals(byteBuffer.position(), input.length);
>>>>>>> upstream/master
    }

}

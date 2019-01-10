package org.baeldung.java.io;


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
    }

}

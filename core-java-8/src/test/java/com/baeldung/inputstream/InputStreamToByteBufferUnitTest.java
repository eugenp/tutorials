package com.baeldung.inputstream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.junit.jupiter.api.Test;

class InputStreamToByteBufferUnitTest {


    @Test
    void when_ReadingInputFileInByteBuffer_then_InputFileAndByteBufferSizeMustMatch() throws IOException {
        String fileName = "frontenac-2257154_960_720.jpg";
        ClassLoader classLoader = new InputStreamToByteBufferUnitTest().getClass().getClassLoader();
        File inputFile = new File(classLoader.getResource(fileName).getFile());
        ByteBuffer bufferByte =
            ByteBuffer.allocate((int) inputFile.length());
        FileInputStream in = new FileInputStream(inputFile);
        in.getChannel().read(bufferByte);
        assertEquals(bufferByte.position(),inputFile.length());
    }
}

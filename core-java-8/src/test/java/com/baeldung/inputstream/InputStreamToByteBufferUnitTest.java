package com.baeldung.inputstream;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputStreamToByteBufferUnitTest {

    @Test
    void givenUsingCoreClasses_whenWritingAFileIntoAByteBuffer_thenBytesLengthMustMatch() throws IOException {

        ClassLoader classLoader = new InputStreamToByteBufferUnitTest()
          .getClass()
          .getClassLoader();

        String fileName = "frontenac-2257154_960_720.jpg";

        File inputFile = new File(classLoader
          .getResource(fileName)
          .getFile());

        ByteBuffer bufferByte = ByteBuffer.allocate((int) inputFile.length());

        FileInputStream in = new FileInputStream(inputFile);

        in
          .getChannel()
          .read(bufferByte);

        assertEquals(bufferByte.position(), inputFile.length());
    }
}

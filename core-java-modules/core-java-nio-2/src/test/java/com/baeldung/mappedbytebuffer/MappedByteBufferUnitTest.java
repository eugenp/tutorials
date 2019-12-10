package com.baeldung.mappedbytebuffer;

import org.junit.Test;

import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MappedByteBufferUnitTest {

    @Test
    public void givenFileChannel_whenReadToTheMappedByteBuffer_thenShouldSuccess() throws Exception {
        // given
        CharBuffer charBuffer = null;
        Path pathToRead = getFileURIFromResources("fileToRead.txt");

        // when
        try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(pathToRead, EnumSet.of(StandardOpenOption.READ))) {
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());

            if (mappedByteBuffer != null) {
                charBuffer = Charset.forName("UTF-8").decode(mappedByteBuffer);
            }
        }

        // then
        assertNotNull(charBuffer);
        assertEquals(charBuffer.toString(), "This is a content of the file");
    }

    @Test
    public void givenPath_whenWriteToItUsingMappedByteBuffer_thenShouldSuccessfullyWrite() throws Exception {
        // given
        final CharBuffer charBuffer = CharBuffer.wrap("This will be written to the file");
        final Path pathToWrite = getFileURIFromResources("fileToWriteTo.txt");

        // when
        try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(pathToWrite, EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING))) {
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, charBuffer.length());

            if (mappedByteBuffer != null) {
                mappedByteBuffer.put(Charset.forName("utf-8").encode(charBuffer));
            }
        }

        // then
        final List<String> fileContent = Files.readAllLines(pathToWrite);
        assertEquals(fileContent.get(0), "This will be written to the file");

    }

    //

    private final Path getFileURIFromResources(String fileName) throws Exception {
        final ClassLoader classLoader = getClass().getClassLoader();
        return Paths.get(classLoader.getResource(fileName).toURI());
    }
}

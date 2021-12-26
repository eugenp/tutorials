package com.baeldung.iostreams;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputSequenceUnitTest {

    private static final String FILE1 = "iostreams/File1.txt";
    private static final String FILE2 = "iostreams/File2.txt";
    private static final String FILE3 = "iostreams/File3.txt";

    private static final String FILE1_AND_FILE2_CONTENT = "InputSequenceUnitTest";
    private static final String ALL_FILES_CONTENT = "InputSequenceUnitTest is Success";

    @Test
    public void givenTwoFiles_readAsString() throws URISyntaxException, IOException {
        String file1 = Paths.get(ClassLoader.getSystemResource(FILE1).toURI()).toString();
        String file2 = Paths.get(ClassLoader.getSystemResource(FILE2).toURI()).toString();
        InputSequenceHandler inputSequenceHandler = new InputSequenceHandler(file1, file2);
        String stringValue = inputSequenceHandler.readAsString();
        inputSequenceHandler.close();
        assertEquals(stringValue, FILE1_AND_FILE2_CONTENT);
    }

    @Test
    public void givenFileList_readAsString() throws URISyntaxException, IOException {
        List<String> filesList = new ArrayList<>();
        filesList.add(Paths.get(ClassLoader.getSystemResource(FILE1).toURI()).toString());
        filesList.add(Paths.get(ClassLoader.getSystemResource(FILE2).toURI()).toString());
        filesList.add(Paths.get(ClassLoader.getSystemResource(FILE3).toURI()).toString());
        InputSequenceHandler inputSequenceHandler = new InputSequenceHandler(filesList);
        String stringValue = inputSequenceHandler.readAsString();
        inputSequenceHandler.close();
        assertEquals(stringValue, ALL_FILES_CONTENT);
    }

    @Test
    public void givenVectorOfInputStreams_readAsString() throws IOException {
        String[] strings = {"Testing", "Leads", "to", "failure",
                "and", "failure", "leads", "to", "understanding"};
        Vector<InputStream> inputStreamVector = new Vector<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (String string: strings) {
            inputStreamVector.add(new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8)));
            stringBuilder.append(string);
        }
        InputSequenceHandler inputSequenceHandler = new InputSequenceHandler(inputStreamVector);
        String combinedString = inputSequenceHandler.readAsString();
        inputSequenceHandler.close();
        assertEquals(stringBuilder.toString(), combinedString);
    }

    @Test
    public void givenTwoStrings_readCombinedValue() throws IOException {
        InputStream first = new ByteArrayInputStream("One".getBytes());
        InputStream second = new ByteArrayInputStream("Magic".getBytes());
        SequenceInputStream sequenceInputStream = new SequenceInputStream(first, second);
        StringBuilder stringBuilder = new StringBuilder();
        int byteValue;
        while ((byteValue = sequenceInputStream.read()) != -1) {
            stringBuilder.append((char) byteValue);
        }
        assertEquals("OneMagic", stringBuilder.toString());
    }
}

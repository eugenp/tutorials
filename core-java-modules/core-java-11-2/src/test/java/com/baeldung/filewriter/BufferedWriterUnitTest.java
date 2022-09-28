package com.baeldung.filewriter;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class BufferedWriterUnitTest {

    private static final ArrayList<String> stringArrayList = new ArrayList<String>(Arrays.asList("Hello", "World"));

    @Test
    public void givenUsingBufferedWriter_whenStringArrayList_thenGetTextFile() throws IOException {
        String fileName = BufferedWriterExample.generateFileFromArrayList(stringArrayList);
        long count = Files.lines(Paths.get(fileName)).count();
        assertTrue("No. of lines in file should be equal to no. of Strings in ArrayList", ((int) count) == stringArrayList.size());
    }
}

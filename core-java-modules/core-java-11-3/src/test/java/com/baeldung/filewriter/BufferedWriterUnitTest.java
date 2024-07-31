package com.baeldung.filewriter;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class BufferedWriterUnitTest {

    private static final List<String> stringList = Arrays.asList("Hello", "World");

    @Test
    public void givenUsingBufferedWriter_whenStringList_thenGetTextFile() throws IOException {
        String fileName = BufferedWriterExample.generateFileFromStringList(stringList);
        long count = Files.lines(Paths.get(fileName)).count();
        assertTrue("No. of lines in file should be equal to no. of Strings in List", ((int) count) == stringList.size());
    }
}

package com.baeldung.filewriter;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import static org.junit.Assert.assertTrue;


public class FileWriteStringUnitTest {

    private static final ArrayList<String> stringArrayList = new ArrayList<String>() {
        {
            add("Hello");
            add("World");
        }
    };

    @Test
    public void givenUsingFileWriteString_whenStringArrayList_thenGetTextFile() throws IOException {
        String fileName = FileWriteStringExample.generateFileFromArrayList(stringArrayList);
        long count = Files.lines(Paths.get(fileName)).count();
        assertTrue("No. of lines in file should be equal to no. of Strings in ArrayList", ((int) count) == stringArrayList.size());
    }
}

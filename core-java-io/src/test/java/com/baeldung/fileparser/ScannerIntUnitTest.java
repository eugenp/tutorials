package com.baeldung.fileparser;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class ScannerIntUnitTest {

    protected static final String NUMBER_FILENAME = "src/test/resources/sampleNumberFile.txt";
    
    @Test
    public void whenParsingExistingTextFile_thenGetIntArrayList() throws IOException {
        List<Integer> numbers = ScannerIntExample.generateArrayListFromFile(NUMBER_FILENAME);
        assertTrue("File does not has 2 lines", numbers.size() == 2);
    }
}

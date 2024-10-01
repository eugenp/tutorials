package com.baeldung.fileparser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

class CommonsFileUtilsExampleUnitTest {

    private static final String TEXT_FILENAME = "src/test/resources/sampleTextFile.txt";

    @Test
    void whenParsingExistingTextFile_thenGetArrayList() throws IOException {
        List<String> lines = CommonsFileUtilsExample.generateArrayListFromFile(TEXT_FILENAME);
        assertTrue(lines.size() == 2, "File does not has 2 lines");
    }

}

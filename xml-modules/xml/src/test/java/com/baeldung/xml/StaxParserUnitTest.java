package com.baeldung.xml;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.baeldung.xml.binding.Tutorial;

public class StaxParserUnitTest {

    final String fileName = "src/test/resources/example_stax.xml";

    StaxParser parser;

    @Test
    public void getAllTutorialsTest() {
        parser = new StaxParser(new File(fileName));
        List<Tutorial> tutorials = parser.getAllTutorial();

        assertNotNull(tutorials);
        assertTrue(tutorials.size() == 4);
        assertTrue(tutorials.get(0).getType().equalsIgnoreCase("java"));
    }
}

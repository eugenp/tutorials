package com.baeldung.xml;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.baeldung.xml.binding.Tutorials;

public class JaxbParserUnitTest {

    final String fileName = "src/test/resources/example_jaxb.xml";

    JaxbParser parser;

    @Test
    public void getFullDocumentTest() {
        parser = new JaxbParser(new File(fileName));
        Tutorials tutorials = parser.getFullDocument();

        assertNotNull(tutorials);
        assertTrue(tutorials.getTutorial().size() == 4);
        assertTrue(tutorials.getTutorial().get(0).getType().equalsIgnoreCase("java"));
    }

    @Test
    public void createNewDocumentTest() {
        File newFile = new File("src/test/resources/example_jaxb_new.xml");
        parser = new JaxbParser(newFile);
        parser.createNewDocument();

        assertTrue(newFile.exists());

        Tutorials tutorials = parser.getFullDocument();

        assertNotNull(tutorials);
        assertTrue(tutorials.getTutorial().size() == 1);
        assertTrue(tutorials.getTutorial().get(0).getTitle().equalsIgnoreCase("XML with Jaxb"));
    }
}

package com.baeldung.xml;

import com.baeldung.xml.binding.Tutorials;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JaxbParserTest {

	
	private final String fileName = "src/test/resources/example.xml";

	private JaxbParser parser;
	
	@Test
	public void getFullDocumentTest(){
		parser = new JaxbParser(new File(fileName));
		Tutorials tutorials = parser.getFullDocument();

		assertNotNull(tutorials);
		assertTrue(tutorials.getTutorial().size() == 4);
		assertTrue(tutorials.getTutorial().get(0).getType().equalsIgnoreCase("java"));
	}
	
	@Test
	public void createNewDocumentTest(){
		File newFile = new File("src/test/resources/example_new.xml");
		parser = new JaxbParser(newFile);
		parser.createNewDocument();

		
		assertTrue(newFile.exists());

		Tutorials tutorials = parser.getFullDocument();

		assertNotNull(tutorials);
		assertTrue(tutorials.getTutorial().size() == 1);
		assertTrue(tutorials.getTutorial().get(0).getTitle().equalsIgnoreCase("XML with Jaxb"));
	}
}

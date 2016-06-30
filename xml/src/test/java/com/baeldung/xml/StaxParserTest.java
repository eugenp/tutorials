package com.baeldung.xml;

import com.baeldung.xml.model.Tutorial;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class StaxParserTest {

	private final String fileName = "src/test/resources/example.xml";

	private StaxParser parser;
	
	@Test
	public void getAllTutorialsTest(){
		parser = new StaxParser(new File(fileName));
		List<Tutorial> tutorials = parser.getAllTutorial();
		
		assertNotNull(tutorials);
		assertTrue(tutorials.size() == 4);
		assertTrue(tutorials.get(0).getType().equalsIgnoreCase("java"));
	}
}

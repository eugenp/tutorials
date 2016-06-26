package com.baeldung.xml;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JaxenDemoTest {

	private final String fileName = "src/test/resources/example.xml";
	
	private JaxenDemo jaxenDemo;
	
	@Test
    public void getFirstLevelNodeListTest() {
		jaxenDemo = new JaxenDemo(new File(fileName));
        List<?> list = jaxenDemo.getAllTutorial();

        assertNotNull(list);
        assertTrue(list.size() == 4);
    }
}

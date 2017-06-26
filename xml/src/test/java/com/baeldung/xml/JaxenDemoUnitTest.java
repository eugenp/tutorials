package com.baeldung.xml;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;

public class JaxenDemoUnitTest {

    final String fileName = "src/test/resources/example_jaxen.xml";

    JaxenDemo jaxenDemo;

    @Test
    public void getFirstLevelNodeListTest() {
        jaxenDemo = new JaxenDemo(new File(fileName));
        List<?> list = jaxenDemo.getAllTutorial();

        assertNotNull(list);
        assertTrue(list.size() == 4);
    }
}

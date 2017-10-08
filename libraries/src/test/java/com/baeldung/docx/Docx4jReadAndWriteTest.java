package com.baeldung.docx;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class Docx4jReadAndWriteTest {

    String imagePath = "src/main/resources/image.jpg";
    String outputPath = "helloWorld.docx";
    
    @Test
    public void givenWordPackage_whenTextExist_thenReturnTrue() {
        Docx4jExample docx4j = new Docx4jExample();
        try {
            docx4j.createDocumentPackage(outputPath, imagePath);
            assertTrue(docx4j.isTextExist("Hello World!"));
            assertTrue(!docx4j.isTextExist("InexistantText"));
        } catch (Exception e) {
            fail();
        }
    }
}

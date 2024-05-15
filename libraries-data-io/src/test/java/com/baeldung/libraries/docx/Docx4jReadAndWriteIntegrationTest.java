package com.baeldung.libraries.docx;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class Docx4jReadAndWriteIntegrationTest {

    private static final String imagePath = "src/main/resources/image.jpg";
    private static final String outputPath = "helloWorld.docx";

    @Test
    public void givenWordPackage_whenTextExist_thenReturnTrue() throws Exception {
        Docx4jExample docx4j = new Docx4jExample();
        docx4j.createDocumentPackage(outputPath, imagePath);
        assertTrue(docx4j.isTextExist("Hello World!"));
        assertTrue(!docx4j.isTextExist("InexistantText"));
    }
}

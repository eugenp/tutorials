package com.baeldung.xslt;

import org.junit.jupiter.api.Test;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class XSLTProcessorUnitTest {

    @Test
    public void givenValidInputAndStylesheet_whenTransformingXML_thenOutputHTMLCreated() throws TransformerException, IOException {
        // Given
        String inputXMLPath = "src/test/resources/input.xml";
        String xsltPath = "src/test/resources/stylesheet.xslt";
        String outputHTMLPath = "src/test/resources/output.html";

      
        XSLTProcessor.transformXMLUsingXSLT(inputXMLPath, xsltPath, outputHTMLPath);

      
        Path outputFile = Paths.get(outputHTMLPath);
        assertTrue(Files.exists(outputFile));
    }
}

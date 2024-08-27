package com.baeldung.apache.xslt;

import com.baeldung.apache.xslt.XSLTProcessor;
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
        String inputXMLPath = "src/test/resources/xslt/input.xml";
        String xsltPath = "src/test/resources/xslt/stylesheet.xslt";
        String outputHTMLPath = "src/test/resources/xslt/output.html";
        XSLTProcessor.transformXMLUsingXSLT(inputXMLPath, xsltPath, outputHTMLPath);
        Path outputFile = Paths.get(outputHTMLPath);
        assertTrue(Files.exists(outputFile));
    }

}

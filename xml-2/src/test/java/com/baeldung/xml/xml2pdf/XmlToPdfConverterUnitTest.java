package com.baeldung.xml.xml2pdf;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class XmlToPdfConverterUnitTest {

    private static final String BASE_PATH = "src/main/resources/xmltopdf/";

    private static final String STYLE_XSL = BASE_PATH + "style.xsl";
    private static final String DATA_XML_INPUT = BASE_PATH + "data-input.xml";
    private static final String DATA_PDF_OUTPUT = BASE_PATH + "data-output.pdf";

    // Comment out this method if you need to keep the outputted PDF file.
    @AfterEach
    public void teardown() {
        new File(DATA_PDF_OUTPUT).delete();
    }

    @Test
    public void givenXmlFile_whenConvertToPdfUsingFop_thenPdfGenerated() throws Exception {
        // Execute XML to PDF conversion
        XmlToPdfConverter.convertXMLtoPDFUsingFop(DATA_XML_INPUT, STYLE_XSL, DATA_PDF_OUTPUT);
        // Check if PDF file was created
        File pdfFile = new File(DATA_PDF_OUTPUT);
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.isFile());
        assertTrue(pdfFile.length() > 0);

    }

    @Test
    public void givenXmlFile_whenConvertToPdfUsingIText_thenPdfGenerated() throws Exception {
        // Execute XML to PDF conversion
        XmlToPdfConverter.convertXMLtoPDFUsingIText(DATA_XML_INPUT, DATA_PDF_OUTPUT);
        // Check if PDF file was created
        File pdfFile = new File(DATA_PDF_OUTPUT);
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.isFile());
        assertTrue(pdfFile.length() > 0);
    }

}

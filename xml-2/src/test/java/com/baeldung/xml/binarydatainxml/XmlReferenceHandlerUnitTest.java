package com.baeldung.xml.binarydatainxml;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlReferenceHandlerUnitTest {
    @Test
    public void testProcessXml() {
        XmlReferenceHandler xmlReferenceHandler = new XmlReferenceHandler();
        String xmlContent = "<image><data><reference>external-data.xml</reference></data></image>";
        String processedXml = xmlReferenceHandler.processXml(xmlContent);

        // Replace with actual data
        assertEquals("<image><data>Binary data for external file</data></image>", processedXml);
    }
}
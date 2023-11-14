package com.baeldung.xml.binary;

import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class XmlOptimizedPackgingUnitTest {

    @Test
    public void testMarshalToXml() {
        // Arrange
        byte[] binaryData = "TestBinaryData".getBytes();
        XopExample xopExample = new XopExample(binaryData);

        try {
            // Act
            String xmlContent = xopExample.marshalToXml(xopExample);

            // Assert
            assertNotNull(xmlContent);
            // Add more specific assertions based on your XML structure
        } catch (JAXBException e) {
            // Handle JAXBException
            e.printStackTrace();
        }
    }

    // Add more tests as needed
}

package com.baeldung.xml.binary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class XmlOptimizedPackagingUnitTest {

    @Test
    public void testMarshalToXml() {
        // Arrange
        byte[] binaryData = "TestBinaryData".getBytes();
        XmlOptimizedPackaging xmlOptimizedPackaging = new XmlOptimizedPackaging(binaryData);

        // Act
        String xmlContent = xmlOptimizedPackaging.marshalToXml();

        // Assert
        assertNotNull(xmlContent);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><XmlOptimizedPackaging><BinaryData>TestBinaryData</BinaryData></XmlOptimizedPackaging>", xmlContent.trim());
    }

    // Add more tests as needed
}

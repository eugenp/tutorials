package com.baeldung.xml.binarydatainxml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class XmlOptimizedPackagingUnitTest {

    @Test
    public void testMarshalToXml() {
        byte[] binaryData = "TestBinaryData".getBytes();
        XmlOptimizedPackaging xmlOptimizedPackaging = new XmlOptimizedPackaging(binaryData);

        String xmlContent = xmlOptimizedPackaging.marshalToXml();

        assertNotNull(xmlContent);
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><XmlOptimizedPackaging><BinaryData>TestBinaryData</BinaryData></XmlOptimizedPackaging>",
                xmlContent.trim());
    }
}
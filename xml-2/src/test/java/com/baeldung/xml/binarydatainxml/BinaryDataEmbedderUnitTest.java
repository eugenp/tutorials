package com.baeldung.xml.binarydatainxml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Base64;

import org.junit.jupiter.api.Test;

public class BinaryDataEmbedderUnitTest {

    @Test
    void testBinaryDataEmbedder() {
        // Define test data
        byte[] binaryData = new byte[] { /* Your binary data here */ };
        String expectedXml = "<image>" +
                "<metadata>" +
                "<format>JPEG</format>" +
                "<width>800</width>" +
                "<height>600</height>" +
                "</metadata>" +
                "<data>" +
                "<encoding>base64</encoding>" +
                "<content>" + Base64.getEncoder().encodeToString(binaryData) + "</content>" +
                "</data>" +
                "</image>";

        // Generate XML from binary data
        String generatedXml = BinaryDataEmbedder.generateXml(binaryData);

        // Assert that generated XML matches expected XML
        assertEquals(expectedXml, generatedXml);
    }
}
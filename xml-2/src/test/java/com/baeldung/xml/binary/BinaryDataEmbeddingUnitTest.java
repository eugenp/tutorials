package com.baeldung.xml.binary;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryDataEmbedderTest {

    @Test
    void BinaryDataEmbeddingUnitTest() {
        byte[] binaryData = new byte[] { /* Your binary data here */ };

        // Encode the binary data into Base64
        String encodedData = Base64.getEncoder().encodeToString(binaryData);

        // Assuming your BinaryDataEmbedder class has a method to generate the XML
        String xml = BinaryDataEmbedder.generateXml(encodedData);

        // Define the expected XML structure
        String expectedXml = "<image>" +
            "<metadata>" +
            "<format>JPEG</format>" +
            "<width>800</width>" +
            "<height>600</height>" +
            "</metadata>" +
            "<data>" +
            "<encoding>base64</encoding>" +
            "<content>" + encodedData + "</content>" +
            "</data>" +
            "</image>";

        // Check if the generated XML matches the expected XML
        assertEquals(expectedXml, xml);
    }
}

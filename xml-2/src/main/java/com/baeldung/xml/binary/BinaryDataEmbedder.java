package com.baeldung.xml.binary;

import java.util.Base64;

public class BinaryDataEmbedder {

    public static String generateXml(byte[] binaryData) {
        // Encode the binary data into Base64
        String encodedData = Base64.getEncoder().encodeToString(binaryData);

        // Define the XML structure
        String xml = "<image>" +
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

        return xml;
    }

    public static void main(String[] args) {
        // Replace placeholder with actual binary data
        byte[] binaryData = new byte[] { /* Your binary data here */ };

        // Generate XML from binary data
        String xml = BinaryDataEmbedder.generateXml(binaryData);

        // Print the generated XML
        System.out.println(xml);
    }
}

package com.baeldung.xml.binary;

import java.util.Base64;

public class BinaryDataEmbedder {
    public static void main(String[] args) {
        byte[] binaryData = new byte[] { /* Your binary data here */ };

        // Encode the binary data into Base64
        String encodedData = Base64.getEncoder().encodeToString(binaryData);

        String xmlContent = "<image>\n" +
            "    <metadata>\n" +
            "        <format>JPEG</format>\n" +
            "        <width>800</width>\n" +
            "        <height>600</height>\n" +
            "    </metadata>\n" +
            "    <data>\n" +
            "        <encoding>base64</encoding>\n" +
            "        <content>\n" +
            encodedData + // Insert the encoded data here
            "        </content>\n" +
            "    </data>\n" +
            "</image>";

        // Print or use the 'xmlContent' as needed
        System.out.println(xmlContent);
    }
}

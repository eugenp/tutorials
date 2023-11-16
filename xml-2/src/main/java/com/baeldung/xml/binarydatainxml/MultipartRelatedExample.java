package com.baeldung.xml.binarydatainxml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class MultipartRelatedExample {

    public static void main(String[] args) throws IOException {
        // Read binary data from a file
        byte[] binaryData = Files.readAllBytes(Path.of("your_binary_file.jpg"));

        // Encode the binary data into Base64
        String encodedData = Base64.getEncoder().encodeToString(binaryData);

        // Create an XML document with inline references using Multipart/Related
        String xmlContent = "<multipart_related>" +
                "<metadata>" +
                "<format>JPEG</format>" +
                "<width>800</width>" +
                "<height>600</height>" +
                "</metadata>" +
                "<data>" +
                "<encoding>multipart/related</encoding>" +
                "<content_id>cid:binary_part</content_id>" +
                "<xml_reference>...inline reference to binary part...</xml_reference>" +
                "</data>" +
                "</multipart_related>";
    }
}
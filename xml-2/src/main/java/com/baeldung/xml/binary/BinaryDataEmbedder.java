package com.baeldung.xml.binary;

import java.util.Base64;

public class BinaryDataEmbedder {
    public static void main(String[] args) {
        byte[] binaryData = new byte[] { /* Your binary data here */ };

        // Encode the binary data into Base64
        String encodedData = Base64.getEncoder().encodeToString(binaryData);

        <image> 
            <metadata> 
                <format>JPEG</format> 
                <width>800</width> 
                <height>600</height> 
            </metadata> 
            <data> 
                <encoding>base64</encoding> 
                <content> encodedData</content> 
            </data> 
        </image>
    }
}

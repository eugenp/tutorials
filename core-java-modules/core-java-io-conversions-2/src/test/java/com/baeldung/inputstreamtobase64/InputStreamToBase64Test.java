package com.baeldung.inputstreamtobase64;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
/**
 * 
 */
public class InputStreamToBase64Test {
    
    /**
     * Test stream to base64 conversion
     * @throws Exception 
     */
    @Test
    public void testWhenAnInputStreamIsConvertedToBase64_thenItCanBeDecoded() throws Exception {
        InputStream sourceStream  = getClass().getClassLoader().getResourceAsStream("logo.png");
        byte[] sourceBytes = IOUtils.toByteArray(sourceStream);
        String encodedString = Base64.getEncoder().encodeToString(sourceBytes); 
        assertNotNull(encodedString);
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        assertNotNull(decodedBytes);
        assertTrue(decodedBytes.length == sourceBytes.length);
        assertTrue(calculateChecksum(decodedBytes) == calculateChecksum(sourceBytes)); 
     }

     /**
      * Calculate a checksum
      * @param bytes array of bytes to check
      * @return the total sum of all bytes
      */
     private int calculateChecksum(byte[] bytes) {
        int checksum = 0; 
        for(int index=0; index < bytes.length; index++) {
            checksum+=bytes[index]; 
        }
        return checksum; 
     }
}

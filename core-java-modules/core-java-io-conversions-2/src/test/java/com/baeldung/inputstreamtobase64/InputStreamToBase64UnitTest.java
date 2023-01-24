package com.baeldung.inputstreamtobase64;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
/**
 * Test the Stream to base64 conversion
 */
public class InputStreamToBase64UnitTest {
    
    /**
     * Test stream to base64 conversion
     * @throws Exception 
     */
    @Test
    public void givenABinaryInputStream_whenItIsConvertedToBase64_thenItCanBeDecoded() throws Exception {
        // given a binary input stream
        InputStream sourceStream  = getClass().getClassLoader().getResourceAsStream("logo.png");
        byte[] sourceBytes = IOUtils.toByteArray(sourceStream);

        // when it is converted to base64
        String encodedString = Base64.getEncoder().encodeToString(sourceBytes); 
        assertNotNull(encodedString);

        // then it can be decoded
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

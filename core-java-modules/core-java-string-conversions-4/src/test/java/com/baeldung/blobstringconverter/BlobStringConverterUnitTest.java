package com.baeldung.blobstringconverter;

import org.junit.jupiter.api.Test;
import java.sql.Blob;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BlobStringConverterUnitTest {

    // Test a standard ASCII string
    @Test
    void givenStandardAsciiString_whenPerformingConversion_thenConversionSuccessful() throws SQLException {
        String originalString = "Hello, world!";
        
        // String to Blob
        Blob blob = BlobStringConverter.stringToBlob(originalString);
        assertNotNull(blob);

        // Blob to String
        String convertedString = BlobStringConverter.blobToString(blob);
        
        // Assert the converted string is equal to the original
        assertEquals(originalString, convertedString);
    }

    // Test a string with special characters (important for correct encoding)
    @Test
    void givenStringWithSpecialCharacters_whenPerformingConversion_thenConversionSuccessful() throws SQLException {
        // String with non-ASCII characters (e.g., Japanese, German umlaut)
        String originalString = "Test: こんにちは, äöü";

        // String to Blob
        Blob blob = BlobStringConverter.stringToBlob(originalString);
        
        // Blob to String
        String convertedString = BlobStringConverter.blobToString(blob);
        
        assertEquals(originalString, convertedString);
    }

    // Test an empty string
    @Test
    void givenEmptyString_whenPerformingConversion_thenConversionSuccessful() throws SQLException {
        String originalString = "";
        
        // String to Blob
        Blob blob = BlobStringConverter.stringToBlob(originalString);
        
        // Blob to String
        String convertedString = BlobStringConverter.blobToString(blob);
        
        assertEquals(originalString, convertedString);
    }

    // Test null input for conversion methods
    @Test
    void givenNullString_whenPerformingConversion_thenConversionSuccessful() throws SQLException {
        // Test String to Blob with null
        assertNull(BlobStringConverter.stringToBlob(null), 
                   "stringToBlob should return null for null input.");
        
        // Test Blob to String with null
        assertNull(BlobStringConverter.blobToString(null), 
                   "blobToString should return null for null input.");
    }
}

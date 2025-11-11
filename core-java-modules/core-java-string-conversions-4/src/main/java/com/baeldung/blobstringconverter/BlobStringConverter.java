package com.baeldung.blobstringconverter;

import java.sql.Blob;
import java.sql.SQLException;
import java.nio.charset.StandardCharsets;
import javax.sql.rowset.serial.SerialBlob;

public class BlobStringConverter {

    /**
     * Converts a java.sql.Blob object to a String using UTF-8 encoding.
     * @param blob The Blob object to convert.
     * @return The String representation of the Blob data.
     * @throws SQLException If a database access error occurs.
     */
    public static String blobToString(Blob blob) throws SQLException {
        if (blob == null) {
            return null;
        }

        long length = blob.length();

        // FIX: Check for zero length to avoid SerialException: Invalid arguments.
        if (length == 0) {
            return ""; 
        }

        if (length > Integer.MAX_VALUE) {
             throw new SQLException("Blob is too large for a single String conversion.");
        }
        
        // Get the entire Blob content as a byte array. The position starts at 1.
        byte[] bytes = blob.getBytes(1, (int) length);

        // Convert the byte array to a String using the UTF-8 charset
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Converts a String object to a java.sql.Blob using UTF-8 encoding.
     * @param text The String to convert.
     * @return A SerialBlob object containing the String data.
     * @throws SQLException If a database access error occurs.
     */
    public static Blob stringToBlob(String text) throws SQLException {
        if (text == null) {
            return null;
        }

        // Convert the String to a byte array using the UTF-8 charset.
        // This is safe even for an empty string ("") which results in a zero-length byte array.
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);

        // Create a SerialBlob object from the byte array
        return new SerialBlob(bytes);
    }  
}

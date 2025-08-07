package com.baeldung.bytearraybytestring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.google.protobuf.ByteString;

public class ByteArrayByteStringUnitTest {
    
    @Test
    public void givenByteArray_whenModified_thenChangesPersist() {
        // Here, we'll initialize a mutable buffer
        byte[] data = new byte[4];

        // We'll read data into the buffer
        ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[] { 0x01, 0x02, 0x03, 0x04 });
        try {
            inputStream.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Note, the first byte is 1
        assertEquals(1, data[0]);

        // We can directly modify the first byte
        data[0] = 0x05;

        // The modification is persisted
        assertEquals(5, data[0]);
    }

    @Test
    public void givenByteString_whenCreated_thenIsImmutable() {
        // We'll create an immutable ByteString from a mutable byte array
        byte[] originalArray = new byte[] { 0x01, 0x02, 0x03, 0x04 };
        ByteString byteString = ByteString.copyFrom(originalArray);

        // The value of the first byte is 1
        assertEquals(1, byteString.byteAt(0));

        // We'll try to modify the original array
        originalArray[0] = 0x05;

        // The ByteString's contents remain unchanged
        assertEquals(1, byteString.byteAt(0));
    }

    @Test
    public void givenByteArray_whenCopiedToByteString_thenDataIsCopied() {
        // We'll start with a mutable byte array
        byte[] byteArray = new byte[] { 0x01, 0x02, 0x03 };

        // Create a new ByteString from it
        ByteString byteString = ByteString.copyFrom(byteArray);

        // We'll assert that the data is the same
        assertEquals(byteArray[0], byteString.byteAt(0));

        // Here, we change the original array
        byteArray[0] = 0x05;

        // Note, the ByteString remains unchanged, confirming the copy
        assertEquals(1, byteString.byteAt(0));
        assertNotSame(byteArray, byteString.toByteArray());
    }

    @Test
    public void givenByteString_whenConvertedToByteArray_thenDataIsCopied() {
        // We'll start with an immutable ByteString
        ByteString byteString = ByteString.copyFromUtf8("Baeldung");

        // We create a mutable byte array from it
        byte[] byteArray = byteString.toByteArray();

        // The byte array now has a copy of the data
        assertEquals('B', (char) byteArray[0]);

        // We change the new array
        byteArray[0] = 'X';

        // The original ByteString remains unchanged
        assertEquals('B', (char) byteString.byteAt(0));
        assertNotSame(byteArray, byteString.toByteArray());
    }
}

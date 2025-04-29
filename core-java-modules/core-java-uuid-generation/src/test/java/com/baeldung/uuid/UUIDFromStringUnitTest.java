package com.baeldung.uuid;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UUIDFromStringUnitTest {
    @Test
    void whenStringInUUIDFormat_thenFromStringWorks() {
        String inputStr = "bbcc4621-d88f-4a94-ae2f-b38072bf5087";
        
        UUID uuid = UUID.fromString(inputStr);
        UUID uuid2 = UUID.fromString(inputStr);
        UUID uuid3 = UUID.fromString(inputStr);

        assertEquals(inputStr, uuid.toString());

        assertEquals(uuid, uuid2);
        assertEquals(uuid, uuid3);

    }

    @Test
    void whenStringNotInUUIDFormat_thenFromStringRaisesException() {
        String inputStr = "I am not a standard UUID representation.";
        assertThrows(IllegalArgumentException.class, () -> UUID.fromString(inputStr));
    }

    @Test
    void whenStringInFreeFormat_thenNameUUIDFromBytesWorks() {
        String inputStr = "I am not a standard UUID representation.";

        UUID uuid = UUID.nameUUIDFromBytes(inputStr.getBytes());
        UUID uuid2 = UUID.nameUUIDFromBytes(inputStr.getBytes());
        UUID uuid3 = UUID.nameUUIDFromBytes(inputStr.getBytes());

        assertTrue(uuid != null);

        assertEquals(uuid, uuid2);
        assertEquals(uuid, uuid3);

        assertEquals(3, uuid.version());
    }

    @Test
    void whenStringInFreeFormat_thenGenerateVer5UUIDWorks() {
        String inputStr = "I am not a standard UUID representation.";

        UUID uuid = UUIDGenerator.generateType5UUID(inputStr);
        UUID uuid2 = UUIDGenerator.generateType5UUID(inputStr);
        UUID uuid3 = UUIDGenerator.generateType5UUID(inputStr);

        assertEquals(5, uuid.version());

        assertTrue(uuid != null);

        assertEquals(uuid, uuid2);
        assertEquals(uuid, uuid3);
    }
}
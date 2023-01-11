package com.baeldung.file;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

class CustomMultipartFileUnitTest {

    @Test
    void whenProvidingByteArray_thenMultipartFileCreated() throws IOException {
        byte[] inputArray = "Test String".getBytes();
        CustomMultipartFile customMultipartFile = new CustomMultipartFile(inputArray);
        Assertions.assertFalse(customMultipartFile.isEmpty());
        Assertions.assertArrayEquals(inputArray, customMultipartFile.getBytes());
        Assertions.assertEquals(inputArray.length,customMultipartFile.getSize());
    }

    @Test
    void whenProvidingByteArray_thenMockMultipartFileCreated() throws IOException {
        byte[] inputArray = "Test String".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("tempFileName", inputArray);
        Assertions.assertFalse(mockMultipartFile.isEmpty());
        Assertions.assertArrayEquals(inputArray, mockMultipartFile.getBytes());
        Assertions.assertEquals(inputArray.length,mockMultipartFile.getSize());
    }
}

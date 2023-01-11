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
        Assertions.assertArrayEquals(inputArray, customMultipartFile.getBytes());
    }

    @Test
    void whenProvidingByteArray_thenMockMultipartFileCreated() throws IOException {
        byte[] inputArray = "Test String".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("tempFileName", inputArray);
        Assertions.assertArrayEquals(inputArray, mockMultipartFile.getBytes());
    }
}

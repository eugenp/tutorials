package com.baeldung.file;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

public class CustomMultipartFileUnitTest {

    @Test
    public void givenByteArray_thenMultipartFileCreated() throws IOException {
        byte[] inputArr = "Test String".getBytes();
        CustomMultipartFile customMultipartFile = new CustomMultipartFile(inputArr);
        Assertions.assertArrayEquals(inputArr, customMultipartFile.getBytes());
    }

    @Test
    public void givenByteArray_thenMockMultipartFileCreated() throws IOException {
        byte[] inputArr = "Test String".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("tempFileName", inputArr);
        Assertions.assertArrayEquals(inputArr, mockMultipartFile.getBytes());
    }
}

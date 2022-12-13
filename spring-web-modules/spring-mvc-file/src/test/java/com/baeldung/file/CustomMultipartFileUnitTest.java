package com.baeldung.file;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

public class CustomMultipartFileUnitTest {

    @Test
    public void givenByteArray_thenMultipartFileCreated() throws IOException {
        byte[] inputArr = "Test String".getBytes();
        CustomMultipartFile customMultipartFile = new CustomMultipartFile(inputArr);
        Assertions.assertEquals(inputArr, customMultipartFile.getBytes());
    }

    @Test
    public void givenByteArray_thenMockMultipartFileCreated() throws IOException {
        byte[] inputArr = "Test String".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("tempFileName", inputArr);
        Assertions.assertEquals(inputArr, mockMultipartFile.getBytes());
    }

    @Test
    public void givenByteArray_thenByteArrayMultipartFileEditorCreated() throws IOException{
        byte[] inputArr = "Test String".getBytes();
        ByteArrayMultipartFileEditor editor = new ByteArrayMultipartFileEditor();
        editor.setValue(inputArr);
        MultipartFile multipartFile = (MultipartFile) editor.getValue();
        Assertions.assertEquals(inputArr,multipartFile.getBytes());
    }
}

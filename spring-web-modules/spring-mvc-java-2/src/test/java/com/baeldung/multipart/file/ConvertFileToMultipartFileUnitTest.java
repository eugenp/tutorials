package com.baeldung.multipart.file;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class ConvertFileToMultipartFileUnitTest {

    @Test
    public void givenFile_whenCreateMultipartFile_thenContentMatch() throws IOException {
        File file = new File("src/main/resources/targetFile.tmp");
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", fileBytes);
        String fileContent = new String(multipartFile.getBytes());
        assertEquals("Hello World", fileContent);
        assertEquals("targetFile.tmp", multipartFile.getOriginalFilename());
    }

    @Test
    public void givenFile_whenCreateMultipartFileUsingCommonsMultipart_thenContentMatch() throws IOException {
        File file = new File("src/main/resources/targetFile.tmp");
        InputStream input = new FileInputStream(file);
        byte [] arr = IOUtils.toByteArray(input);
        OutputStream outputStream = new FileOutputStream(file);
        IOUtils.copy(input, outputStream);
        MultipartFile multipartFile = new MockMultipartFile("test","targetFile.tmp",  MediaType.TEXT_PLAIN_VALUE, arr);
        String fileContent = new String(multipartFile.getBytes());
        assertEquals("Hello World", fileContent);
        assertEquals("targetFile.tmp", multipartFile.getOriginalFilename());
    }

}

package com.baeldung.multipart.file;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.mock.web.MockMultipartFile;

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
        FileItem fileItem = new DiskFileItem("file", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
        InputStream input = new FileInputStream(file);
        OutputStream outputStream = fileItem.getOutputStream();
        IOUtils.copy(input, outputStream);
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        String fileContent = new String(multipartFile.getBytes());
        assertEquals("Hello World", fileContent);
        assertEquals("targetFile.tmp", multipartFile.getOriginalFilename());
    }

}

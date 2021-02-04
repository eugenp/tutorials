package com.baeldung.cloud.openfeign;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import com.baeldung.cloud.openfeign.fileupload.service.UploadService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenFeignFileUploadUnitTest {
    
    @Autowired
    private UploadService uploadService;
    
    private static String FILE_PATH = "fileupload.txt"; // "<Your file Path>";
    
    @Test
    public void whenFeignBuilder_thenFileUploadSuccess() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        File file = new File(classloader.getResource("fileupload.txt").getFile());
        Assert.assertTrue(file.exists());
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
                IOUtils.toByteArray(input));
        // Please uncomment the code once upload service is up and running
        // Assert.assertTrue(uploadService.uploadFileWithManualClient(multipartFile));
    }
    
    @Test
    public void whenAnnotatedFeignClient_thenFileUploadSuccess() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        File file = new File(classloader.getResource("fileupload.txt").getFile());
        Assert.assertTrue(file.exists());
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
                IOUtils.toByteArray(input));
        /*
         * Please uncomment the code once upload service is up and running
         */
        // String uploadFile = uploadService.uploadFile(multipartFile);
        // Assert.assertNotNull(uploadFile);
    }
}

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
public class OpenFeignFileUploadLiveTest {
    
    @Autowired
    private UploadService uploadService;
    
    private static String FILE_NAME = "fileupload.txt";
    
    @Test
    public void whenFeignBuilder_thenFileUploadSuccess() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        File file = new File(classloader.getResource(FILE_NAME).getFile());
        Assert.assertTrue(file.exists());
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
                IOUtils.toByteArray(input));
        Assert.assertTrue(uploadService.uploadFileWithManualClient(multipartFile));
    }
    
    @Test
    public void whenAnnotatedFeignClient_thenFileUploadSuccess() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        File file = new File(classloader.getResource(FILE_NAME).getFile());
        Assert.assertTrue(file.exists());
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
                IOUtils.toByteArray(input));
        String uploadFile = uploadService.uploadFile(multipartFile);
        Assert.assertNotNull(uploadFile);
    }
}

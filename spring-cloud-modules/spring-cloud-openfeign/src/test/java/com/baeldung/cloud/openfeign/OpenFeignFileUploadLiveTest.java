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

import com.baeldung.cloud.openfeign.exception.NotFoundException;
import com.baeldung.cloud.openfeign.fileupload.service.UploadService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenFeignFileUploadLiveTest {
    
    @Autowired
    private UploadService uploadService;
    
    private static String FILE_NAME = "fileupload.txt";
    
    @Test(expected = NotFoundException.class)
    public void whenFileUploadClientFallbackFactory_thenFileUploadError() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        File file = new File(classloader.getResource(FILE_NAME).getFile());
        Assert.assertTrue(file.exists());
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
                IOUtils.toByteArray(input));
        uploadService.uploadFileWithFallbackFactory(multipartFile);
    }

    @Test(expected = NotFoundException.class)
    public void whenFileUploadClientFallback_thenFileUploadError() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        File file = new File(classloader.getResource(FILE_NAME).getFile());
        Assert.assertTrue(file.exists());
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
                IOUtils.toByteArray(input));
        uploadService.uploadFileWithFallback(multipartFile);
    }
    
    @Test(expected = NotFoundException.class)
    public void whenFileUploadWithMannualClient_thenFileUploadError() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        File file = new File(classloader.getResource(FILE_NAME).getFile());
        Assert.assertTrue(file.exists());
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
                IOUtils.toByteArray(input));
        uploadService.uploadFileWithManualClient(multipartFile);
    }
}

package com.baeldung.resource;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = AppConfig.class)
public class SpringResourceTest {

    @Autowired
    private ClassPathResourceReader classPathResourceReader;

    static final String testData = "This is a sample text to demonstrate usage of Spring Resource.";

    @Test
    public void whenManualInstance_thenReadSuccessful() throws IOException {
        Resource resource = classPathResourceReader.constructResourceManually();
        String fileData = classPathResourceReader.listResourceContentsUsingFile(resource.getFile());
        assertEquals(testData, fileData);
    }

    @Test
    public void whenResourceLoader_thenReadSuccessful() throws IOException {
        Resource resource = classPathResourceReader.retrieveResourceUsingResourceLoader();
        String fileData = classPathResourceReader.listResourceContentsUsingFile(resource.getFile());
        assertEquals(testData, fileData);
    }

    @Test
    public void whenApplicationContext_thenReadSuccessful() throws IOException {
        Resource resource = classPathResourceReader.retrieveResourceUsingApplicationContext();
        String fileData = classPathResourceReader.listResourceContentsUsingFile(resource.getFile());
        assertEquals(testData, fileData);
    }

    @Test
    public void whenAutowired_thenReadSuccessful() throws IOException {
        Resource resource = classPathResourceReader.getSampleFile();
        String fileData = classPathResourceReader.listResourceContentsUsingFile(resource.getFile());
        assertEquals(testData, fileData);
    }

    @Test
    public void whenResourceUtils_thenReadSuccessful() throws IOException {
        String fileData = classPathResourceReader.listResourceContentsUsingFile(classPathResourceReader.retrieveFileUsingResourceUtils());
        assertEquals(testData, fileData);
    }

    @Test
    public void whenResourceAsStream_thenReadSuccessful() throws IOException {
        Resource resource = classPathResourceReader.retrieveResourceUsingResourceLoader();
        String fileData = classPathResourceReader.listResourceContentsUsingInputStream(resource.getInputStream());
        assertEquals(testData, fileData);
    }
}

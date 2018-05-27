package com.baeldung.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
public class ClassPathResourceReader {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    ApplicationContext applicationContext;

    @Value("classpath:data/resource-data.txt")
    Resource resourceFile;

    /**
     * Constructs Resource object by making use of its built-in implementations.
     * 
     * @return Resource
     */
    public Resource constructResourceManually() {
        Resource resource = new ClassPathResource("data/resource-data.txt");
        return resource;
    }

    /**
     * Constructs resource object by making use of ResourceLoader. 
     * 
     * @return Resource
     */
    public Resource retrieveResourceUsingResourceLoader() {
        Resource resource = resourceLoader.getResource("classpath:data/resource-data.txt");
        return resource;
    }

    /**
     * Constructs Resource instance by making use of ApplicationContext. 
     * 
     * @return Resource
     */
    public Resource retrieveResourceUsingApplicationContext() {
        Resource resource = applicationContext.getResource("classpath:data/resource-data.txt");
        return resource;
    }

    /**
     * ResourceUtils example for getting file data.
     * 
     * @return
     * @throws FileNotFoundException
     */
    public File retrieveFileUsingResourceUtils() throws FileNotFoundException {
        return ResourceUtils.getFile("classpath:data/resource-data.txt");
    }

    /**
     * Utility method to list contents of a file.
     * 
     * @param fileResource
     * @return
     * @throws IOException
     */
    public String listResourceContentsUsingFile(File fileResource) throws IOException {
        FileReader fileReader = new FileReader(fileResource);
        BufferedReader bufReader = new BufferedReader(fileReader);
        String dataLine = bufReader.readLine();
        StringBuilder fileData = new StringBuilder();
        while (null != dataLine) {
            fileData.append(dataLine);
            dataLine = bufReader.readLine();
        }
        bufReader.close();
        fileReader.close();
        return fileData.toString();
    }

    /**
     * Utility method to list contents of a stream 
     * 
     * @param ipStream
     * @return
     * @throws IOException
     */
    public String listResourceContentsUsingInputStream(InputStream ipStream) throws IOException {
        BufferedReader bufReader = new BufferedReader(new InputStreamReader(ipStream, "UTF-8"));
        String dataLine = bufReader.readLine();
        StringBuilder fileData = new StringBuilder();
        while (null != dataLine) {
            fileData.append(dataLine);
            dataLine = bufReader.readLine();
        }
        bufReader.close();
        return fileData.toString();
    }

    public Resource getSampleFile() {
        return resourceFile;
    }

    public void setSampleFile(Resource sampleFile) {
        this.resourceFile = sampleFile;
    }
}

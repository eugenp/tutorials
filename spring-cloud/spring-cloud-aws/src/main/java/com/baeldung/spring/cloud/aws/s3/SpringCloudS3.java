package com.baeldung.spring.cloud.aws.s3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Component
public class SpringCloudS3 {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    ResourcePatternResolver resourcePatternResolver;

    public void downloadS3Object(String s3Url) throws IOException {
        Resource resource = resourceLoader.getResource(s3Url);
        File downloadedS3Object = new File(resource.getFilename());
        try (InputStream inputStream = resource.getInputStream()) {
            Files.copy(inputStream, downloadedS3Object.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public void uploadFileToS3(File file, String s3Url) throws IOException {
        WritableResource resource = (WritableResource) resourceLoader.getResource(s3Url);
        try (OutputStream outputStream = resource.getOutputStream()) {
            Files.copy(file.toPath(), outputStream);
        }
    }

    public void downloadMultipleS3Objects(String s3UrlPattern) throws IOException {
        Resource[] allFileMatchingPatten = this.resourcePatternResolver.getResources(s3UrlPattern);
        for (Resource resource : allFileMatchingPatten) {
            String fileName = resource.getFilename();
            fileName = fileName.substring(0, fileName.lastIndexOf("/") + 1);
            File downloadedS3Object = new File(fileName);
            try (InputStream inputStream = resource.getInputStream()) {
                Files.copy(inputStream, downloadedS3Object.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}

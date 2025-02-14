package com.baeldung.resttemplate.web.upload.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class MultipartFileUploadClient {

    public static void main(String[] args) throws IOException {
        uploadSingleFile();
        uploadMultipleFile();
    }

    private static void uploadSingleFile() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", getTestFile());


        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        String serverUrl = "http://localhost:8080/spring-rest/fileserver/singlefileupload/";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
        System.out.println("Response code: " + response.getStatusCode());
    }

    private static void uploadMultipleFile() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("files", getTestFile());
        body.add("files", getTestFile());
        body.add("files", getTestFile());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        String serverUrl = "http://localhost:8080/spring-rest/fileserver/multiplefileupload/";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
        System.out.println("Response code: " + response.getStatusCode());
    }

    public static Resource getTestFile() throws IOException {
        Path testFile = Files.createTempFile("test-file", ".txt");
        System.out.println("Creating and Uploading Test File: " + testFile);
        Files.write(testFile, "Hello World !!, This is a test file.".getBytes());
        return new FileSystemResource(testFile.toFile());
    }
}

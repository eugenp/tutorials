package com.baeldung.awss3reactive;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.baeldung.awss3reactive.UploadResult;

import static org.junit.Assert.*;

/**
 * <p>LiveTests for Reactive S3 Application</p>
 * 
 * <p>
 * Please take a look at instructions available at README.md in this folder before running tests.
 * </p>
 * 
 * @author <a href="https://www.baeldung.com/author/philippe-sevestre">psevestre</a>
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("minio") // NOTE: Remove this in order to run tests with AWS !
class ReactiveS3ApplicationLiveTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @LocalServerPort
    private int serverPort;
    

    @Test
    void whenUploadSingleFile_thenSuccess() throws Exception {
        
        String url = "http://localhost:" + serverPort + "/inbox";        
        byte[] data = Files.readAllBytes(Paths.get("src/test/resources/testimage1.png"));         
        UploadResult result = restTemplate.postForObject(url, data , UploadResult.class);
        
        assertEquals("Expected CREATED (202)", result.getStatus(), HttpStatus.CREATED );
        
    }
    
    @Test
    void whenUploadMultipleFiles_thenSuccess() throws Exception {
        
        
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        addFileEntity("f1", body, new File("src/test/resources/testimage1.png"));
        addFileEntity("f2", body, new File("src/test/resources/testimage2.png"));
        
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body);
        String url = "http://localhost:" + serverPort + "/inbox";        
        
        ResponseEntity<UploadResult> result = restTemplate.postForEntity(url, requestEntity, UploadResult.class);

        assertEquals("Http Code",HttpStatus.CREATED, result.getStatusCode() );
        assertEquals("File keys",2, result.getBody().getKeys().length);
        
    }

    private void addFileEntity(String name, MultiValueMap<String, Object> body, File file) throws Exception {

        byte[] data = Files.readAllBytes(file.toPath());
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        ContentDisposition contentDispositionHeader = ContentDisposition.builder("form-data")
          .name(name)
          .filename(file.getName())
          .build();
        
        headers.add(HttpHeaders.CONTENT_DISPOSITION, contentDispositionHeader.toString());
        
        HttpEntity<byte[]> fileEntity = new HttpEntity<>(data, headers);
        body.add(name, fileEntity);
    }

    
}

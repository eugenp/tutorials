package com.baeldung.s3proxy;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import net.bytebuddy.utility.RandomString;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.BucketAlreadyOwnedByYouException;
import software.amazon.awssdk.services.s3.model.S3Object;

@ActiveProfiles("gcp")
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(classes = Application.class)
@EnableConfigurationProperties(StorageProperties.class)
class GcpCloudStorageLiveTest {

    @Autowired
    private S3Client s3Client;

    @Autowired
    private StorageProperties storageProperties;

    @BeforeAll
    void setup() {
        String bucketName = storageProperties.getBucketName();
        try {
            s3Client.createBucket(request -> request.bucket(bucketName));            
        } catch (BucketAlreadyOwnedByYouException exception) {
            // do nothing
        }
    }

    @Test
    void whenFileUploaded_thenFileSavedInGcpCloudStorageBucket() throws Exception {
        // Prepare test file to upload
        String key = RandomString.make(10) + ".txt";
        String fileContent = RandomString.make(50);
        MultipartFile fileToUpload = createTextFile(key, fileContent);
        
        // Save file to GCP cloud storage bucket
        s3Client.putObject(request -> 
            request
                .bucket(storageProperties.getBucketName())
                .key(key)
                .contentType(fileToUpload.getContentType())
            , RequestBody.fromBytes(fileToUpload.getBytes()));
        
        // Verify that the file is saved successfully by checking if it exists in the bucket
        List<S3Object> savedObjects = s3Client.listObjects(request -> 
            request.bucket(storageProperties.getBucketName())
        ).contents();
        assertThat(savedObjects)
            .anyMatch(savedObject -> savedObject.key().equals(key));
    }

    private MultipartFile createTextFile(String fileName, String content) throws IOException {
        byte[] fileContentBytes = content.getBytes();
        InputStream inputStream = new ByteArrayInputStream(fileContentBytes);
        return new MockMultipartFile(fileName, fileName, "text/plain", inputStream);
    }

}
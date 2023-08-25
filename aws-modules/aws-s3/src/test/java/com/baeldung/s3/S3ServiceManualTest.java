package com.baeldung.s3;


import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import software.amazon.awssdk.services.s3.S3Client;

/**
 * Required defined environment variables AWS_ACCESS_KEY_ID & AWS_ACCESS_KEY to access S3.
 * Required S3 bucket and key that exist.
 */

class S3ServiceManualTest {

    private static final String BUCKET_NAME = "bucket_name";
    private static final String KEY_NAME = "key_name";
    @Mock
    private S3Client s3Client;

    private S3Service s3Service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        s3Service = new S3Service(s3Client);
    }

    @AfterEach
    public void cleanup() {
        s3Service.cleanup();
        Mockito.verify(s3Client).close();
    }

    @Test
    void whenVerifyIfObjectExistByDefaultMethod_thenCorrect() {
        assertTrue(s3Service.doesObjectExistByDefaultMethod(BUCKET_NAME, KEY_NAME), "Key: " + KEY_NAME + " doesn't exist");
    }

    @Test
    void whenVerifyIfObjectExistByListObjects_thenCorrect() {
        assertTrue(s3Service.doesObjectExistByListObjects(BUCKET_NAME, KEY_NAME), "Key: " + KEY_NAME + " doesn't exist");
    }
}

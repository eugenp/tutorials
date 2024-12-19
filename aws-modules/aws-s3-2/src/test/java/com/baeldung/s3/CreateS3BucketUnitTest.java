package com.baeldung.s3;

import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.amazonaws.services.s3.AmazonS3;

class CreateS3BucketUnitTest {

    private AmazonS3 s3Client = Mockito.mock(AmazonS3.class);

    @Test
    void whenVerifyingCreationOfS3Bucket_thenCorrect() {
        String bucketName = "bucket-name-" + UUID.randomUUID();

        CreateS3Bucket createS3Bucket = new CreateS3Bucket(this.s3Client);
        createS3Bucket.createBucket(bucketName);

        verify(this.s3Client).createBucket(bucketName);
    }

}
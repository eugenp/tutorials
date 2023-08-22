package com.baeldung.s3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.CopyObjectResponse;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;

class S3ServiceIntegrationTest {

    private static final String BUCKET_NAME = "bucket_name";
    private static final String KEY_NAME = "key_name";
    private static final String BUCKET_NAME2 = "bucket_name2";
    private static final String KEY_NAME2 = "key_name2";

    @Mock
    private S3Client s3Client;

    private S3Service s3Service;

    private final String AWS_BUCKET = "baeldung-tutorial-s3";

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
    void whenInitializingAWSS3Service_thenNotNull() {
        assertThat(new S3Service(s3Client)).isNotNull();
    }

    @Test
    void whenVerifyingIfS3BucketExist_thenCorrect() {
        HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
            .bucket(BUCKET_NAME)
            .build();

        s3Service.doesBucketExist(BUCKET_NAME);
        verify(s3Client).headBucket(headBucketRequest);
    }

    @Test
    void whenVerifyingCreationOfS3Bucket_thenCorrect() {
        CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
            .bucket(BUCKET_NAME)
            .build();

        s3Service.createBucket(BUCKET_NAME);
        verify(s3Client).createBucket(bucketRequest);
    }

    @Test
    void whenVerifyingListBuckets_thenCorrect() {
        when(s3Client.listBuckets()).thenReturn(ListBucketsResponse.builder().buckets(Collections.emptyList()).build());

        s3Service.listBuckets();
        Mockito.verify(s3Client).listBuckets();
    }

    @Test
    void whenDeletingBucket_thenCorrect() {
        DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder()
            .bucket(BUCKET_NAME)
            .build();

        s3Service.deleteBucket(BUCKET_NAME);
        verify(s3Client).deleteBucket(deleteBucketRequest);
    }



    @Test
    void whenVerifyingListObjects_thenCorrect() {
        ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(AWS_BUCKET).build();
        ListObjectsV2Response response = ListObjectsV2Response.builder().contents(Collections.emptyList()).build();

        when(s3Client.listObjectsV2(request)).thenReturn(response);

        s3Service.listObjects(AWS_BUCKET);
        verify(s3Client).listObjectsV2(request);
    }


    @Test
    void whenVerifyingCopyObject_thenCorrect() {
        CopyObjectRequest request = CopyObjectRequest.builder()
            .sourceBucket(BUCKET_NAME)
            .sourceKey(KEY_NAME)
            .destinationBucket(BUCKET_NAME2)
            .destinationKey(KEY_NAME2)
            .build();

        CopyObjectResponse result = CopyObjectResponse.builder().build();

        when(s3Client.copyObject(request)).thenReturn(result);

        assertThat(s3Service.copyObject(BUCKET_NAME, KEY_NAME, BUCKET_NAME2, KEY_NAME2)).isEqualTo(result);
        verify(s3Client).copyObject(request);
    }

    @Test
    void whenVerifyingDeleteObject_thenCorrect() {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
            .bucket(BUCKET_NAME)
            .key(KEY_NAME)
            .build();

        s3Service.deleteObject(BUCKET_NAME, KEY_NAME);
        verify(s3Client).deleteObject(deleteObjectRequest);
    }
}

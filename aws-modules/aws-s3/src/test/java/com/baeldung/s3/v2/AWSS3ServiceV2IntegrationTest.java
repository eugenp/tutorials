package com.baeldung.s3.v2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.CopyObjectResponse;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectsResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;

public class AWSS3ServiceV2IntegrationTest {

    private static final String BUCKET_NAME = "bucket_name"; 
    private static final String KEY_NAME = "key_name"; 
    private static final String BUCKET_NAME2 = "bucket_name2"; 
    private static final String KEY_NAME2 = "key_name2";

    @Mock
    private S3Client s3Client;

    private AWSS3ServiceV2 s3Service;

    private String AWS_BUCKET = "baeldung-tutorial-s3";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        s3Service = new AWSS3ServiceV2(s3Client);
    }

    @AfterEach
    public void cleanup() {
        s3Service.cleanup();
        Mockito.verify(s3Client).close();
    }
    @Test 
    public void whenInitializingAWSS3Service_thenNotNull() { 
        assertThat(new AWSS3ServiceV2(s3Client)).isNotNull();
    } 
    
    @Test 
    public void whenVerifyingIfS3BucketExist_thenCorrect() {
        HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
            .bucket(BUCKET_NAME)
            .build();

        s3Service.doesBucketExist(BUCKET_NAME);
        verify(s3Client).headBucket(headBucketRequest);
    } 
 
    @Test 
    public void whenVerifyingCreationOfS3Bucket_thenCorrect() {
        CreateBucketRequest request = mock(CreateBucketRequest.class);

        s3Service.createBucket(BUCKET_NAME);
        verify(s3Client).createBucket(request);
    } 
 
    @Test 
    public void whenVerifyingListBuckets_thenCorrect() {
        s3Service.listBuckets();
        verify(s3Client).listBuckets();
    } 

    @Test 
    public void whenDeletingBucket_thenCorrect() {
        DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder()
            .bucket(BUCKET_NAME)
            .build();

        s3Service.deleteBucket(BUCKET_NAME);
        verify(s3Client).deleteBucket(deleteBucketRequest);
    } 
    
    @Test 
    public void whenVerifyingPutObject_thenCorrect() { 
//        File file = mock(File.class);
//        PutObjectResult result = mock(PutObjectResult.class);
//        when(s3.putObject(anyString(), anyString(), (File) any())).thenReturn(result);
//
//        assertThat(service.putObject(BUCKET_NAME, KEY_NAME, file)).isEqualTo(result);
//        verify(s3).putObject(BUCKET_NAME, KEY_NAME, file);
    }
    
    @Test 
    public void whenVerifyingListObjects_thenCorrect() {
        ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(BUCKET_NAME).build();
        ListObjectsV2Response response = ListObjectsV2Response.builder().contents(Collections.emptyList()).build();

        when(s3Client.listObjectsV2(request)).thenReturn(response);

        s3Service.listObjects(AWS_BUCKET);
        Mockito.verify(s3Client).listObjectsV2(request);
    } 
    
    @Test 
    public void whenVerifyingGetObject_thenCorrect() {
        GetObjectRequest objectRequest = GetObjectRequest.builder()
            .bucket(BUCKET_NAME)
            .key(KEY_NAME)
            .build();

        s3Service.getObject(BUCKET_NAME, KEY_NAME);
        verify(s3Client).getObject(objectRequest);
    } 
    
    @Test 
    public void whenVerifyingCopyObject_thenCorrect() {
        CopyObjectRequest request = mock(CopyObjectRequest.class);
        CopyObjectResponse result = mock(CopyObjectResponse.class);

        when(s3Client.copyObject(request)).thenReturn(result);
 
        assertThat(s3Service.copyObject(BUCKET_NAME, KEY_NAME, BUCKET_NAME2, KEY_NAME2)).isEqualTo(result);
        verify(s3Client).copyObject(request);
    } 
    
    @Test 
    public void whenVerifyingDeleteObject_thenCorrect() {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
            .bucket(BUCKET_NAME)
            .key(KEY_NAME)
            .build();

        s3Service.deleteObject(BUCKET_NAME, KEY_NAME);
        verify(s3Client).deleteObject(deleteObjectRequest);
    }
    
    @Test 
    public void whenVerifyingDeleteObjects_thenCorrect() {
        List<String> objKeyList = List.of("Document/hello2.txt", "Document/picture.png");
        DeleteObjectsRequest request = mock(DeleteObjectsRequest.class);
        DeleteObjectsResponse response = mock(DeleteObjectsResponse.class);

        when(s3Client.deleteObjects((DeleteObjectsRequest)any())).thenReturn(response);
        
        s3Service.deleteObjects(BUCKET_NAME, objKeyList);
        verify(s3Client).deleteObjects(request);
    }
    
}

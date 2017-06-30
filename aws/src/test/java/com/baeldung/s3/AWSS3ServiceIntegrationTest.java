package com.baeldung.s3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*; 

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult; 

public class AWSS3ServiceIntegrationTest {

    private static final String BUCKET_NAME = "bucket_name"; 
    private static final String KEY_NAME = "key_name"; 
 
    private AmazonS3 s3; 
    private AWSS3Service service; 
 
    @Before 
    public void setUp() { 
        s3 = mock(AmazonS3.class); 
        service = new AWSS3Service(s3); 
    } 
 
    @Test 
    public void createService() { 
        assertThat(new AWSS3Service()).isNotNull(); 
    } 
    
    @Test 
    public void doesBucketExist() { 
        service.doesBucketExist(BUCKET_NAME); 
        verify(s3).doesBucketExist(BUCKET_NAME); 
    } 
 
    @Test 
    public void createBucket() { 
        service.createBucket(BUCKET_NAME); 
        verify(s3).createBucket(BUCKET_NAME); 
    } 
 
    @Test 
    public void listBuckets() { 
        service.listBuckets(); 
        verify(s3).listBuckets(); 
    } 
    
    @Test 
    public void putObject() { 
        File file = mock(File.class); 
        PutObjectResult result = mock(PutObjectResult.class); 
        when(s3.putObject(anyString(), anyString(), (File) any())).thenReturn(result); 
 
        assertThat(service.putObject(BUCKET_NAME, KEY_NAME, file)).isEqualTo(result); 
        verify(s3).putObject(BUCKET_NAME, KEY_NAME, file); 
    }
    
    @Test 
    public void listObjects() { 
        service.listObjects(BUCKET_NAME); 
        verify(s3).listObjects(BUCKET_NAME); 
    } 
    
    @Test 
    public void deleteBucket() { 
        service.deleteBucket(BUCKET_NAME); 
        verify(s3).deleteBucket(BUCKET_NAME); 
    } 
}
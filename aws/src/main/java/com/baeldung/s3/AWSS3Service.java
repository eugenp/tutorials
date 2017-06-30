package com.baeldung.s3;

import java.io.File;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectResult;

public class AWSS3Service {
    private final AmazonS3 s3client;

    public AWSS3Service() { 
        this(new AmazonS3Client() {
        }); 
    } 
    
    public AWSS3Service(AmazonS3 s3client) {
        this.s3client = s3client;
    }
    
    public boolean doesBucketExist(String bucketName) { 
        return s3client.doesBucketExist(bucketName); 
    } 
    
    public Bucket createBucket(String bucketName) { 
        return s3client.createBucket(bucketName); 
    } 

    public List<Bucket> listBuckets() { 
        return s3client.listBuckets(); 
    } 
    
    public PutObjectResult putObject(String bucketName, String key, File file) {
        return s3client.putObject(bucketName, key, file);
    }
    
    public ObjectListing listObjects(String bucketName) {
        return s3client.listObjects(bucketName);
    }
    
    public void deleteBucket(String bucketName) { 
        s3client.deleteBucket(bucketName); 
    } 
}

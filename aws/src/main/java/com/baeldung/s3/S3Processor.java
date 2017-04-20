package com.baeldung.s3;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.baeldung.AWSBaseCredentials;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class S3Processor extends AWSBaseCredentials {
    private AmazonS3Client amazonS3Client;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public S3Processor() {
        AmazonS3ClientBuilder amazonS3ClientBuilder = AmazonS3Client.builder();
        amazonS3Client = (AmazonS3Client) amazonS3ClientBuilder
                .withCredentials(credentialsProvider)
                .withRegion(Regions.US_WEST_2)
                .build();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        S3Processor s3Processor = new S3Processor();

        String bucketName = "baeldung-s3-bucket";

        s3Processor.createBucket(bucketName);

        Thread.sleep(50000);

        s3Processor.listBuckets();

        s3Processor.createFolder("folder1", bucketName);

        s3Processor.uploadObject(bucketName, "file1", "filePath");

        s3Processor.deleteObject(bucketName, "file1");

        s3Processor.deleteObject(bucketName, "folder1/"); // Delete Folder

        s3Processor.listObjects("baeldung");

        s3Processor.deleteBucket(bucketName);

        Thread.sleep(50000);

        s3Processor.listBuckets();
    }

    public void createBucket(String bucketName) {
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
        createBucketRequest.setCannedAcl(CannedAccessControlList.PublicRead);
        Bucket bucket = amazonS3Client.createBucket(createBucketRequest);

//        amazonS3Client.createBucket(bucketName);

        System.out.println(gson.toJson(bucket));
    }

    public void createFolder(String folderName, String bucketName) {
        InputStream emptyInputStream = new ByteArrayInputStream(new byte[0]);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(0);

//        PutObjectResult response = amazonS3Client.putObject(bucketName, folderName + "/", emptyInputStream, objectMetadata);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName + "/", emptyInputStream, objectMetadata);
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        PutObjectResult response = amazonS3Client.putObject(putObjectRequest);

    }

    public void uploadObject(String bucketName, String objectName, String filePath) throws IOException {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new File(filePath));
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);

//        PutObjectResult putObjectResult = amazonS3Client.putObject(bucketName, objectName, new File(filePath));
    }

    public void deleteObject(String bucketName, String objectName) {
        amazonS3Client.deleteObject(bucketName, objectName);

        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, objectName);
        amazonS3Client.deleteObject(deleteObjectRequest);
    }

    public void listObjects(String bucketName) {
//        ObjectListing objectListing = amazonS3Client.listObjects(bucketName);

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.withBucketName(bucketName);
        listObjectsRequest.withPrefix("bael");
        ObjectListing objectListing = amazonS3Client.listObjects(listObjectsRequest);

        objectListing.getObjectSummaries().forEach(System.out::println);
    }

    public void deleteBucket(String bucketName) {
        DeleteBucketRequest deleteBucketRequest = new DeleteBucketRequest(bucketName);
        amazonS3Client.deleteBucket(deleteBucketRequest);

//        amazonS3Client.deleteBucket(bucketName);
    }

    public void listBuckets() {
        List<Bucket> buckets = amazonS3Client.listBuckets();
        buckets.forEach(System.out::println);
    }
}

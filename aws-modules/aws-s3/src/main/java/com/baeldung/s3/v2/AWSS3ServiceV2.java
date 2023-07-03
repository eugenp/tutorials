package com.baeldung.s3.v2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;

import com.amazonaws.AmazonServiceException;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.CopyObjectResponse;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.Delete;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;

public class AWSS3ServiceV2 {

    private S3Client s3Client;

    public AWSS3ServiceV2(Region awsRegion) {
        init(awsRegion);
    }

    public AWSS3ServiceV2(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void init(Region awsRegion) {
        this.s3Client = S3Client.builder()
            .region(awsRegion)
            .credentialsProvider(ProfileCredentialsProvider.create("default"))
            .build();
    }
    
    //is bucket exist?
    public boolean doesBucketExist(String bucketName) {
        HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
            .bucket(bucketName)
            .build();

        try {
            s3Client.headBucket(headBucketRequest);
            return true;
        } catch (NoSuchBucketException e) {
            return false;
        }
    } 
    
    //create a bucket
    public void createBucket(String bucketName) {
        CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
            .bucket(bucketName)
            .build();

        s3Client.createBucket(bucketRequest);
    }

    //list all buckets
    public void listBuckets() {
        ListBucketsResponse listBucketsResponse = s3Client.listBuckets();
        for(Bucket s : listBucketsResponse.buckets() ) {
            System.out.println(s.name());
        }
    }

    //delete a bucket
    public void deleteBucket(String bucketName) {
        try {
            DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder()
                .bucket(bucketName)
                .build();

            s3Client.deleteBucket(deleteBucketRequest);
            System.out.println("Successfully deleted bucket : " + bucketName);
        } catch (S3Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }  
    
    //uploading object
    public PutObjectResponse putObject(String bucketName, String key, File file) {
        PutObjectRequest request = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build();

        return s3Client.putObject(request, Path.of(file.toURI()) );
    }
    
    //listing objects
    public void listObjects(String bucketName) {
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
            .bucket(bucketName)
            .build();
        ListObjectsV2Response listObjectsV2Response = s3Client.listObjectsV2(listObjectsV2Request);

        for(S3Object os : listObjectsV2Response.contents()) {
            System.out.println(os.key());
        }
    }
    
    //get an object
    public void getObject(String bucketName, String objectKey) {
        try {
            GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

            ResponseBytes<GetObjectResponse> responseResponseBytes = s3Client.getObjectAsBytes(objectRequest);

            byte[] data = responseResponseBytes.asByteArray();

            // Write the data to a local file.
            File myFile = new File("/Users/user/Desktop/hello.txt" );
            OutputStream os = new FileOutputStream(myFile);
            os.write(data);
            System.out.println("Successfully obtained bytes from an S3 object");
            os.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    } 
    
    //copying an object
    public CopyObjectResponse copyObject(
      String sourceBucketName,
      String sourceKey,
      String destinationBucketName,
      String destinationKey
    ) {
        CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder()
            .sourceBucket(sourceBucketName)
            .sourceKey(sourceKey)
            .destinationBucket(destinationBucketName)
            .destinationKey(destinationKey)
            .build();

        return s3Client.copyObject(copyObjectRequest);
    }
    
    //deleting an object
    public void deleteObject(String bucketName, String objectKey) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
            .bucket(bucketName)
            .key(objectKey)
            .build();

        s3Client.deleteObject(deleteObjectRequest);
    }
    
    //deleting multiple Objects
    public void deleteObjects(String bucketName, List<String> keys ) {

        ArrayList<ObjectIdentifier> toDelete = new ArrayList<>();
        for(String objKey : keys) {
            toDelete.add(ObjectIdentifier.builder()
                .key(objKey)
                .build());
        }

        DeleteObjectsRequest deleteObjectRequest = DeleteObjectsRequest.builder()
            .bucket(bucketName)
            .delete(Delete.builder()
                .objects(toDelete).build())
            .build();

        s3Client.deleteObjects(deleteObjectRequest);
    }

    public void cleanup() {
        this.s3Client.close();
    }
}

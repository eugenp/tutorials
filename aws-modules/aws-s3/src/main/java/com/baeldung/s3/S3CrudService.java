package com.baeldung.s3;

import java.util.Optional;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class S3CrudService {

    private final S3Client s3Client;

    public S3CrudService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void createBucket(String bucketName) {
        CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
          .bucket(bucketName)
          .build();

        s3Client.createBucket(bucketRequest);
    }

    public void createObject(String bucketName, File inMemoryObject) {
        PutObjectRequest request = PutObjectRequest.builder()
          .bucket(bucketName)
          .key(inMemoryObject.getName())
          .build();
        s3Client.putObject(request, RequestBody.fromByteBuffer(inMemoryObject.getContent()));
    }

    public Optional<byte[]> getObject(String bucketName, String objectKey) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
              .bucket(bucketName)
              .key(objectKey)
              .build();
            ResponseBytes<GetObjectResponse> responseResponseBytes = s3Client.getObjectAsBytes(getObjectRequest);
            return Optional.of(responseResponseBytes.asByteArray());
        } catch (S3Exception e) {
            return Optional.empty();
        }
    }

    public boolean deleteObject(String bucketName, String objectKey) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
              .bucket(bucketName)
              .key(objectKey)
              .build();

            s3Client.deleteObject(deleteObjectRequest);
            return true;
        } catch (S3Exception e) {
            return false;
        }
    }
}

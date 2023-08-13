package com.baeldung.s3;

import java.util.List;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

public class RenameObjectService {

    private S3Client s3Client;

    public RenameObjectService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public RenameObjectService() {
        init();
    }

    public void init() {
        this.s3Client = S3Client.builder()
          .region(Region.US_EAST_1)
          .credentialsProvider(ProfileCredentialsProvider.create("default"))
          .build();
    }

    public void renameFile(String bucketName, String keyName, String destinationKeyName) {
        CopyObjectRequest copyObjRequest = CopyObjectRequest.builder()
          .sourceBucket(bucketName)
          .sourceKey(keyName)
          .destinationBucket(destinationKeyName)
          .destinationKey(bucketName)
          .build();
        s3Client.copyObject(copyObjRequest);
        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
          .bucket(bucketName)
          .key(keyName)
          .build();
        s3Client.deleteObject(deleteRequest);
    }

    public void renameFolder(String bucketName, String sourceFolderKey, String destinationFolderKey) {
        ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
          .bucket(bucketName)
          .prefix(sourceFolderKey)
          .build();

        ListObjectsV2Response listResponse = s3Client.listObjectsV2(listRequest);
        List<S3Object> objects = listResponse.contents();

        for (S3Object s3Object : objects) {
            String newKey = destinationFolderKey + s3Object.key()
              .substring(sourceFolderKey.length());

            // Copy object to destination folder
            CopyObjectRequest copyRequest = CopyObjectRequest.builder()
              .sourceBucket(bucketName)
              .sourceKey(s3Object.key())
              .destinationBucket(bucketName)
              .destinationKey(newKey)
              .build();
            s3Client.copyObject(copyRequest);

            // Delete object from source folder
            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
              .bucket(bucketName)
              .key(s3Object.key())
              .build();
            s3Client.deleteObject(deleteRequest);
        }
    }

}

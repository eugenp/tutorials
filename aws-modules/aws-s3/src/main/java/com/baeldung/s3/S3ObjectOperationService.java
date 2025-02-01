package com.baeldung.s3;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;

class S3ObjectOperationService {

    private final S3Client s3Client;

    public S3ObjectOperationService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void upload(String bucketName, File file) {
        upload(bucketName, file, null);
    }

    public void upload(String bucketName, File file, Map<String, String> metadata) {
        s3Client
            .putObject(request -> 
                request
                    .bucket(bucketName)
                    .key(file.getName())
                    .metadata(metadata)
                    .ifNoneMatch("*"), 
                file.toPath());
    }

    public void download(String bucketName, String key, Path downloadPath) {
        s3Client.getObject(request ->
            request
                .bucket(bucketName)
                .key(key),
            ResponseTransformer.toFile(downloadPath));
    }

    public void copyObject(String sourceBucketName, String sourceKey, 
        String destinationBucketName, String destinationKey) {
        s3Client.copyObject(request -> 
            request
                .sourceBucket(sourceBucketName)
                .sourceKey(sourceKey)
                .destinationBucket(destinationBucketName)
                .destinationKey(destinationKey));
    }

    public void delete(String bucketName, String key) {
        s3Client.deleteObject(request -> 
            request
                .bucket(bucketName)
                .key(key));
    }

    public void delete(String bucketName, List<String> keys) {
        List<ObjectIdentifier> objectsToDelete = keys
            .stream()
            .map(key -> ObjectIdentifier
                .builder()
                .key(key)
                .build())
            .toList();

        s3Client.deleteObjects(request -> 
            request
                .bucket(bucketName)
                .delete(deleteRequest -> 
                    deleteRequest
                        .objects(objectsToDelete)));
    }

}
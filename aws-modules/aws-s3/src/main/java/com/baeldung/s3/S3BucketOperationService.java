package com.baeldung.s3;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.S3Exception;

class S3BucketOperationService {

    private final S3Client s3Client;

    public S3BucketOperationService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public boolean bucketExists(String bucketName) {
        try {
            s3Client.headBucket(request -> request.bucket(bucketName));
            return true;
        } catch (NoSuchBucketException exception) {
            return false;
        }
    }

    public void createBucket(String bucketName) {
        s3Client.createBucket(request -> request.bucket(bucketName));
    }

    public List<Bucket> listBuckets() {
        List<Bucket> allBuckets = new ArrayList<>();
        String nextToken = null;

        do {
            String continuationToken = nextToken;
            ListBucketsResponse listBucketsResponse = s3Client.listBuckets(
                request -> request.continuationToken(continuationToken)
            );

            allBuckets.addAll(listBucketsResponse.buckets());
            nextToken = listBucketsResponse.continuationToken();
        } while (nextToken != null);
        return allBuckets;
    }

    public void deleteBucket(String bucketName) {
        try {
            s3Client.deleteBucket(request -> request.bucket(bucketName));
        } catch (S3Exception exception) {
            if (exception.statusCode() == HttpStatus.SC_CONFLICT) {
                throw new BucketNotEmptyException();
            }
            throw exception;
        }
    }

}
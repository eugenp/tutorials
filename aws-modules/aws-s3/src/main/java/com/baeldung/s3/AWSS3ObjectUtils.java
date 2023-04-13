package com.baeldung.s3;

import org.apache.http.HttpStatus;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;

public class AWSS3ObjectUtils {

    private AmazonS3 s3Client;

    public AWSS3ObjectUtils(AmazonS3 s3client) {
        this.s3Client = s3client;
    }

    public boolean doesObjectExistByDefaultMethod(String bucket, String key) {
        return s3Client.doesObjectExist(bucket, key);
    }

    public boolean doesObjectExistByListObjects(String bucket, String key) {
        return s3Client.listObjects(bucket)
            .getObjectSummaries()
            .stream()
            .filter(s3ObjectSummary -> s3ObjectSummary.getKey()
                .equals(key))
            .findFirst()
            .isPresent();
    }

    public boolean doesObjectExistByMetaData(String bucket, String key) {
        try {
            s3Client.getObjectMetadata(bucket, key);
            return true;
        } catch (AmazonServiceException e) {
            if (e.getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                return false;
            } else {
                throw e;
            }
        }
    }
}
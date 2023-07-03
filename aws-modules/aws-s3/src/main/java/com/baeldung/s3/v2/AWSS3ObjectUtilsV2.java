package com.baeldung.s3.v2;

import org.apache.http.HttpStatus;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class AWSS3ObjectUtilsV2 {

    private S3Client s3Client;

    public AWSS3ObjectUtilsV2(Region awsRegion) {
        init(awsRegion);
    }

    public AWSS3ObjectUtilsV2(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void init(Region awsRegion) {
        this.s3Client = S3Client.builder()
            .region(awsRegion)
            .credentialsProvider(ProfileCredentialsProvider.create("default"))
            .build();
    }

    public boolean doesObjectExistByDefaultMethod(String bucket, String key) {
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

            s3Client.headObject(headObjectRequest);

            System.out.println("Object exists");
            return true;
        } catch (S3Exception e) {
            if (e.statusCode() == 404) {
                System.out.println("Object does not exist");
                return false;
            } else {
                throw e;
            }
        }
    }

}
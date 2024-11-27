package com.baeldung.s3;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

class CreateS3Bucket {

    private final Log log = LogFactory.getLog(CreateS3Bucket.class);

    private AmazonS3 s3Client;

    public CreateS3Bucket(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public CreateS3Bucket() {
        AWSCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
        this.s3Client = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentialsProvider.getCredentials()))
            .withRegion(Regions.EU_CENTRAL_1)
            .build();
    }

    public void createBucket(String bucketName) throws SdkClientException, AmazonServiceException {
        try {
            if (!this.s3Client.doesBucketExistV2(bucketName)) {
                this.s3Client.createBucket(bucketName);
            }

            String bucketRegion = this.s3Client.getBucketLocation(bucketName);
            log.info(bucketRegion);

        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it and returned an error response.
            throw new AmazonServiceException(e.getErrorMessage());
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            throw new SdkClientException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new CreateS3Bucket().createBucket("bucket-name-" + UUID.randomUUID());
    }

}

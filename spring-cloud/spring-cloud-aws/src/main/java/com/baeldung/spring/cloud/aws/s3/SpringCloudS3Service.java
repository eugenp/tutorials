package com.baeldung.spring.cloud.aws.s3;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Component
public class SpringCloudS3Service {

    private static final Logger logger = LoggerFactory.getLogger(SpringCloudS3Service.class);

    @Autowired
    AmazonS3 amazonS3;

    @Autowired
    SpringCloudS3 springCloudS3;

    public void createBucket(String bucketName) {
        logger.debug("Creating S3 bucket: {}", bucketName);
        amazonS3.createBucket(bucketName);
        logger.info("{} bucket created successfully", bucketName);
    }

    public void downloadObject(String bucketName, String objectName) {
        String s3Url = "s3://" + bucketName + "/" + objectName;
        try {
            springCloudS3.downloadS3Object(s3Url);
            logger.info("{} file download result: {}", objectName, new File(objectName).exists());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void uploadObject(String bucketName, String objectName) {
        String s3Url = "s3://" + bucketName + "/" + objectName;
        File file = new File(objectName);
        try {
            springCloudS3.uploadFileToS3(file, s3Url);
            logger.info("{} file uploaded to S3", objectName);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void deleteBucket(String bucketName) {
        logger.trace("Deleting S3 objects under {} bucket...", bucketName);
        ListObjectsV2Result listObjectsV2Result = amazonS3.listObjectsV2(bucketName);
        for (S3ObjectSummary objectSummary : listObjectsV2Result.getObjectSummaries()) {
            logger.info("Deleting S3 object: {}", objectSummary.getKey());
            amazonS3.deleteObject(bucketName, objectSummary.getKey());
        }
        logger.info("Deleting S3 bucket: {}", bucketName);
        amazonS3.deleteBucket(bucketName);
    }

}

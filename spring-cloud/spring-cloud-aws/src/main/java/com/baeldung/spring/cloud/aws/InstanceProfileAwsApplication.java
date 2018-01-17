package com.baeldung.spring.cloud.aws;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.baeldung.spring.cloud.aws.s3.SpringCloudS3;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.baeldung.spring.cloud.aws.s3")
public class InstanceProfileAwsApplication {

    private static final String applicationConfig = "spring.config.name:application-instance-profile";
    private static final Logger logger = LoggerFactory.getLogger(InstanceProfileAwsApplication.class);

    private static String bucketName = "instance-profile-baeldung";
    private static String downloadFileName = "download-file.txt";
    private static String uploadFileName = "upload-file.txt";

    public static void setup(AmazonS3 amazonS3) {
        logger.trace("Setting up S3 bucket....");
        logger.info("Creating S3 bucket: {}", bucketName);
        amazonS3.createBucket(bucketName);
        logger.info("Putting S3 object: {}", uploadFileName);
        amazonS3.putObject(bucketName, downloadFileName, "Hello World");
        try {
            Files.write(Paths.get(uploadFileName), "Hello World Uploaded!".getBytes());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void s3ObjectDownload(SpringCloudS3 springCloudS3) {
        String s3Url = "s3://" + bucketName + "/" + downloadFileName;
        try {
            springCloudS3.downloadS3Object(s3Url);
            logger.info("{} file download result: {}", downloadFileName, new File(downloadFileName).exists());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void s3ObjectUpload(SpringCloudS3 springCloudS3) {
        String s3Url = "s3://" + bucketName + "/" + uploadFileName;
        File file = new File(uploadFileName);
        try {
            springCloudS3.uploadFileToS3(file, s3Url);
            logger.info("{} file uploaded to S3", uploadFileName);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void clean(AmazonS3 amazonS3) {
        logger.trace("Cleaning resources ...");
        ListObjectsV2Result listObjectsV2Result = amazonS3.listObjectsV2(bucketName);
        for (S3ObjectSummary objectSummary : listObjectsV2Result.getObjectSummaries()) {
            logger.info("Deleting S3 object: {}", objectSummary.getKey());
            amazonS3.deleteObject(bucketName, objectSummary.getKey());
        }
        logger.info("Deleting S3 bucket: {}", bucketName);
        amazonS3.deleteBucket(bucketName);
        logger.info("Deleting downloaded file: {}", downloadFileName);
        new File(downloadFileName).delete();
        logger.info("Deleting uploaded file: {}", uploadFileName);
        new File(uploadFileName).delete();
    }

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(InstanceProfileAwsApplication.class);
        builder.properties(applicationConfig);
        builder.build();
        ConfigurableApplicationContext context = builder.run(args);
        AmazonS3 s3 = context.getBean(AmazonS3.class);
        SpringCloudS3 springCloudS3 = context.getBean(SpringCloudS3.class);
        setup(s3);
        s3ObjectUpload(springCloudS3);
        s3ObjectDownload(springCloudS3);
        clean(s3);
    }

}

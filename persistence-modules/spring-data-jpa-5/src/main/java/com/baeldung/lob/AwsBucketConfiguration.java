package com.baeldung.lob;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
class AwsBucketConfiguration {

    @Value("${S3_MODEL_ASSETS_BUCKET_ACCESS_KEY:key}")
    String s3AccessKey;

    @Value("${S3_MODEL_ASSETS_BUCKET_SECRET_KEY:secret}")
    String s3SecretKey;

    @Bean
    AmazonS3 getAmazonS3() {
        AWSCredentials credentials = new BasicAWSCredentials(s3AccessKey, s3SecretKey);
        return AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .build();
    }
}

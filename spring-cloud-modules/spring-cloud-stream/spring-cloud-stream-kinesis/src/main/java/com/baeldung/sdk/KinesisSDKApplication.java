package com.baeldung.sdk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;

@SpringBootApplication
public class KinesisSDKApplication {

	@Value("${aws.access.key}")
	private String accessKey;

	@Value("${aws.secret.key}")
	private String secretKey;

	public static void main(String[] args) {
		SpringApplication.run(KinesisSDKApplication.class, args);
	}

	@Bean
	public AmazonKinesis buildAmazonKinesis() {
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		return AmazonKinesisClientBuilder.standard()
		    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
		    .withRegion(Regions.EU_CENTRAL_1)
		    .build();
	}
}
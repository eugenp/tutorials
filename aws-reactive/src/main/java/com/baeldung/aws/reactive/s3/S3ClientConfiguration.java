package com.baeldung.aws.reactive.s3;

import java.time.Duration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3AsyncClientBuilder;
import software.amazon.awssdk.services.s3.S3Configuration;

@Configuration
@EnableConfigurationProperties(S3ClientConfigurarionProperties.class)
public class S3ClientConfiguration {
	
	@Bean
	public S3AsyncClient s3client(S3ClientConfigurarionProperties s3props,AwsCredentialsProvider credentialsProvider) {
		
		SdkAsyncHttpClient httpClient = NettyNioAsyncHttpClient
		   .builder()
		   .writeTimeout(Duration.ZERO)
		   .maxConcurrency(64)
		   .build();
		
		S3AsyncClientBuilder b = S3AsyncClient.builder().httpClient(httpClient);
		
		if ( s3props.getRegion() != null ) {
			b = b.region(s3props.getRegion());
		}
		
		if ( s3props.getEndpoint() != null ) {
			b = b.endpointOverride(s3props.getEndpoint());
		}
		
		b = b.credentialsProvider(credentialsProvider);
		
		S3Configuration serviceConfiguration = S3Configuration
		  .builder()
		  .checksumValidationEnabled(false)
		  .chunkedEncodingEnabled(true)
		  .build();
		
		b = b.serviceConfiguration(serviceConfiguration);
		
		return b.build();
	}
	
	
	@Bean
	public AwsCredentialsProvider awsCredentialsProvider(S3ClientConfigurarionProperties s3props) {
		
		AwsCredentialsProvider provider = () -> { 
			AwsCredentials creds = AwsBasicCredentials.create(s3props.getAccessKeyId(),s3props.getSecretAccessKey());
			return creds;
		};
		
		return provider;
	}

}

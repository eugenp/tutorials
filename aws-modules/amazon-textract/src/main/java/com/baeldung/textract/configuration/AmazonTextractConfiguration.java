package com.baeldung.textract.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.textract.TextractClient;

@Configuration
@EnableConfigurationProperties(AwsConfigurationProperties.class)
public class AmazonTextractConfiguration {

    private final AwsConfigurationProperties awsConfigurationProperties;

    public AmazonTextractConfiguration(AwsConfigurationProperties awsConfigurationProperties) {
        this.awsConfigurationProperties = awsConfigurationProperties;
    }

    @Bean
    public TextractClient textractClient() {
        String region = awsConfigurationProperties.getRegion();
        String accessKey = awsConfigurationProperties.getAccessKey();
        String secretKey = awsConfigurationProperties.getSecretKey();
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);

        return TextractClient.builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
            .build();
    }

}

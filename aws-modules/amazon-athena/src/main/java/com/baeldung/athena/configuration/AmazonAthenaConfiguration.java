package com.baeldung.athena.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.athena.AthenaClient;
import software.amazon.awssdk.services.athena.model.QueryExecutionContext;
import software.amazon.awssdk.services.athena.model.ResultConfiguration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(AwsConfigurationProperties.class)
public class AmazonAthenaConfiguration {

    private final AwsConfigurationProperties awsConfigurationProperties;

    @Bean
    public AthenaClient athenaClient() {
        return AthenaClient.builder()
                .credentialsProvider(constructCredentials())
                .build();
    }
    
    @Bean
    public QueryExecutionContext queryExecutionContext() {
        final var database = awsConfigurationProperties.getAthena().getDatabase();
        return QueryExecutionContext.builder()
                .database(database)
                .build();
    }
    
    @Bean
    public ResultConfiguration resultConfiguration() {
        final var outputLocation = awsConfigurationProperties.getAthena().getS3OutputLocation();
        return ResultConfiguration.builder()
                .outputLocation(outputLocation)
                .build();
    }

    private StaticCredentialsProvider constructCredentials() {
        final var accessKey = awsConfigurationProperties.getAccessKey();
        final var secretKey = awsConfigurationProperties.getSecretKey();
        final var awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);
        return StaticCredentialsProvider.create(awsCredentials);
    }

}

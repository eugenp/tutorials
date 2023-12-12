package com.baeldung.spring.cloud.aws.sqs;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@SpringBootTest
@Testcontainers
public class BaseSqsIntegrationTest {

    private static final String LOCAL_STACK_VERSION = "localstack/localstack:2.3.2";

    @Container
    static LocalStackContainer localstack = new LocalStackContainer(DockerImageName.parse(LOCAL_STACK_VERSION));

    @Configuration
    public static class SqsLiveTestConfiguration {

        @Bean
        SqsAsyncClient sqsAsyncClient(AwsCredentialsProvider credentialsProvider) {
            return SqsAsyncClient.builder()
                    .region(Region.of(localstack.getRegion()))
                    .credentialsProvider(credentialsProvider)
                    .endpointOverride(localstack.getEndpoint()).build();
        }

        @Bean
        AwsCredentialsProvider credentialsProvider() {
            return StaticCredentialsProvider
                    .create(AwsBasicCredentials.create(localstack.getAccessKey(), localstack.getSecretKey()));
        }
    }

}

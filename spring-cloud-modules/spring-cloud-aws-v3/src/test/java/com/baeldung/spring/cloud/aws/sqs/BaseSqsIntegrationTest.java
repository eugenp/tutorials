package com.baeldung.spring.cloud.aws.sqs;

import java.util.Map;

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
import software.amazon.awssdk.services.sqs.model.QueueAttributeName;

@SpringBootTest
@Testcontainers
public class BaseSqsIntegrationTest {

    private static final String LOCAL_STACK_VERSION = "localstack/localstack:2.3.2";

    @Container
    static LocalStackContainer localstack = new LocalStackContainer(DockerImageName.parse(LOCAL_STACK_VERSION));

    @Configuration
    static class SqsLiveTestConfiguration {

        @Bean
        SqsAsyncClient sqsAsyncClient(AwsCredentialsProvider credentialsProvider) {
            return createQueue(SqsAsyncClient.builder()
                .region(Region.of(localstack.getRegion()))
                .credentialsProvider(credentialsProvider)
                .endpointOverride(localstack.getEndpoint())
                .build());
        }

        @Bean
        AwsCredentialsProvider credentialsProvider() {
            return StaticCredentialsProvider.create(AwsBasicCredentials.create(localstack.getAccessKey(), localstack.getSecretKey()));
        }

        // Framework doesn't support automatically creating FIFO queues at this moment.
        private static SqsAsyncClient createQueue(SqsAsyncClient client) {
            client.createQueue(req -> req.queueName("user_created_queue.fifo")
                .attributes(Map.of(QueueAttributeName.FIFO_QUEUE, "true"))).join();
            return client;
        }

    }

}

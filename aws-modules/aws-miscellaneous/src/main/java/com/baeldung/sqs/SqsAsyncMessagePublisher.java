package com.baeldung.sqs;

import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import java.util.concurrent.CompletableFuture;

public class SqsAsyncMessagePublisher {

    private final SqsAsyncClient sqsAsyncClient;

    public SqsAsyncMessagePublisher(SqsAsyncClient sqsAsyncClient) {
        this.sqsAsyncClient = sqsAsyncClient;
    }

    public CompletableFuture<String> publishMessage(String queueUrl, String messageBody) {
        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .build();

        return sqsAsyncClient.sendMessage(request)
                .thenApply(SendMessageResponse::messageId);
    }
}

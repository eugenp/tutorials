package com.baeldung.sqs;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

public class SqsMessagePublisher {

    private final SqsClient sqsClient;

    public SqsMessagePublisher(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    public String publishMessage(String queueUrl, String messageBody) {
        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .build();

        SendMessageResponse response = sqsClient.sendMessage(request);
        return response.messageId();
    }
}

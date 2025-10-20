package com.baeldung.sqs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;
import java.util.concurrent.CompletableFuture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SqsAsyncMessagePublisherTest {

    @Mock
    private SqsAsyncClient sqsAsyncClient;

    @InjectMocks
    private SqsAsyncMessagePublisher messagePublisher;

    @Test
    void whenPublishMessage_thenMessageIsSentAsynchronously() throws Exception {
        // Arrange
        String queueUrl = "https://sqs.us-east-1.amazonaws.com/123456789012/MyAsyncQueue";
        String messageBody = "Hello, Async SQS!";
        String expectedMessageId = "test-async-message-id-456";

        SendMessageResponse mockResponse = SendMessageResponse.builder()
                .messageId(expectedMessageId)
                .build();
        when(sqsAsyncClient.sendMessage(any(SendMessageRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(mockResponse));

        // Act
        String actualMessageId = messagePublisher.publishMessage(queueUrl, messageBody).get();

        // Assert
        assertThat(actualMessageId).isEqualTo(expectedMessageId);

        ArgumentCaptor<SendMessageRequest> requestCaptor = ArgumentCaptor.forClass(SendMessageRequest.class);
        verify(sqsAsyncClient).sendMessage(requestCaptor.capture());

        SendMessageRequest capturedRequest = requestCaptor.getValue();
        assertThat(capturedRequest.queueUrl()).isEqualTo(queueUrl);
        assertThat(capturedRequest.messageBody()).isEqualTo(messageBody);
    }
}
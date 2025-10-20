package com.baeldung.sqs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SqsMessagePublisherUnitTest {

    @Mock
    private SqsClient sqsClient;

    @InjectMocks
    private SqsMessagePublisher messagePublisher;

    @Test
    void whenPublishMessage_thenMessageIsSentWithCorrectParameters() {
        // Arrange
        String queueUrl = "https://sqs.us-east-1.amazonaws.com/123456789012/MyQueue";
        String messageBody = "Hello, SQS!";
        String expectedMessageId = "test-message-id-123";

        SendMessageResponse mockResponse = SendMessageResponse.builder()
                .messageId(expectedMessageId)
                .build();
        when(sqsClient.sendMessage(any(SendMessageRequest.class))).thenReturn(mockResponse);

        // Act
        String actualMessageId = messagePublisher.publishMessage(queueUrl, messageBody);

        // Assert
        assertThat(actualMessageId).isEqualTo(expectedMessageId);

        ArgumentCaptor<SendMessageRequest> requestCaptor = ArgumentCaptor.forClass(SendMessageRequest.class);
        verify(sqsClient).sendMessage(requestCaptor.capture());

        SendMessageRequest capturedRequest = requestCaptor.getValue();
        assertThat(capturedRequest.queueUrl()).isEqualTo(queueUrl);
        assertThat(capturedRequest.messageBody()).isEqualTo(messageBody);
    }
}
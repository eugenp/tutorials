package com.baeldung.sqs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueAttributesRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueAttributesResponse;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.MessageAttributeValue;
import software.amazon.awssdk.services.sqs.model.QueueAttributeName;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequestEntry;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SetQueueAttributesRequest;

public class SQSApplication {

    private static final String STANDARD_QUEUE_NAME = "baeldung-queue";
    private static final String FIFO_QUEUE_NAME = "baeldung-queue.fifo";
    private static final String DEAD_LETTER_QUEUE_NAME = "baeldung-dead-letter-queue";

    public static void main(String[] args) {

        // Set up the client
        SqsClient sqsClient = SqsClient.builder()
            .region(Region.US_EAST_1)
            .credentialsProvider(ProfileCredentialsProvider.create())
            .build();

        // Create a standard queue
        CreateQueueRequest createStandardQueueRequest = CreateQueueRequest.builder()
            .queueName(STANDARD_QUEUE_NAME)
            .build();

        sqsClient.createQueue(createStandardQueueRequest);

        System.out.println("\nGet queue url");

        GetQueueUrlResponse getQueueUrlResponse = sqsClient.getQueueUrl(GetQueueUrlRequest.builder()
            .queueName(STANDARD_QUEUE_NAME)
            .build());
        String standardQueueUrl = getQueueUrlResponse.queueUrl();

        System.out.println(standardQueueUrl);

        // Create a fifo queue
        Map<QueueAttributeName, String> queueAttributes = new HashMap<>();
        queueAttributes.put(QueueAttributeName.FIFO_QUEUE, "true");
        queueAttributes.put(QueueAttributeName.CONTENT_BASED_DEDUPLICATION, "true");

        CreateQueueRequest createFifoQueueRequest = CreateQueueRequest.builder()
            .queueName(FIFO_QUEUE_NAME)
            .attributes(queueAttributes)
            .build();

        sqsClient.createQueue(createFifoQueueRequest);

        GetQueueUrlResponse getFifoQueueUrlResponse = sqsClient.getQueueUrl(GetQueueUrlRequest.builder()
            .queueName(FIFO_QUEUE_NAME)
            .build());

        String fifoQueueUrl = getFifoQueueUrlResponse.queueUrl();

        System.out.println(fifoQueueUrl);

        // Set up a dead letter queue
        CreateQueueRequest createDeadLetterQueueRequest = CreateQueueRequest.builder()
            .queueName(DEAD_LETTER_QUEUE_NAME)
            .build();

        String deadLetterQueueUrl = sqsClient.createQueue(createDeadLetterQueueRequest)
            .queueUrl();

        GetQueueAttributesRequest getQueueAttributesRequest = GetQueueAttributesRequest.builder()
            .queueUrl(deadLetterQueueUrl)
            .attributeNames(QueueAttributeName.QUEUE_ARN)
            .build();

        GetQueueAttributesResponse deadLetterQueueAttributes = sqsClient.getQueueAttributes(getQueueAttributesRequest);

        String deadLetterQueueARN = deadLetterQueueAttributes.attributes()
            .get("QueueArn");

        Map<QueueAttributeName, String> attributes = new HashMap<>();
        attributes.put(QueueAttributeName.REDRIVE_POLICY, "{\"maxReceiveCount\":\"5\", \"deadLetterTargetArn\":\"" + deadLetterQueueARN + "\"}");

        SetQueueAttributesRequest queueAttributesRequest = SetQueueAttributesRequest.builder()
            .queueUrl(standardQueueUrl)
            .attributes(attributes)
            .build();

        sqsClient.setQueueAttributes(queueAttributesRequest);

        // Send a message to a standard queue

        Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
        MessageAttributeValue messageAttributeValue = MessageAttributeValue.builder()
            .stringValue("This is an attribute")
            .dataType("String")
            .build();

        messageAttributes.put("AttributeOne", messageAttributeValue);

        SendMessageRequest sendMessageStandardQueue = SendMessageRequest.builder()
            .queueUrl(standardQueueUrl)
            .messageBody("A simple message.")
            .delaySeconds(30) // Message will arrive in the queue after 30 seconds. We can use this only in standard queues
            .messageAttributes(messageAttributes)
            .build();

        sqsClient.sendMessage(sendMessageStandardQueue);

        // Send a message to a fifo queue

        SendMessageRequest sendMessageFifoQueue = SendMessageRequest.builder()
            .queueUrl(fifoQueueUrl)
            .messageBody("FIFO Queue")
            .messageGroupId("baeldung-group-1")
            .messageAttributes(messageAttributes)
            .build();

        sqsClient.sendMessage(sendMessageFifoQueue);

        // Send multiple messages

        List<SendMessageBatchRequestEntry> messageEntries = new ArrayList<>();
        SendMessageBatchRequestEntry messageBatchRequestEntry1 = SendMessageBatchRequestEntry.builder()
            .id("id-1")
            .messageBody("batch-1")
            .messageGroupId("baeldung-group-1")
            .build();

        SendMessageBatchRequestEntry messageBatchRequestEntry2 = SendMessageBatchRequestEntry.builder()
            .id("id-2")
            .messageBody("batch-2")
            .messageGroupId("baeldung-group-1")
            .build();

        messageEntries.add(messageBatchRequestEntry1);
        messageEntries.add(messageBatchRequestEntry2);

        SendMessageBatchRequest sendMessageBatchRequest = SendMessageBatchRequest.builder()
            .queueUrl(fifoQueueUrl)
            .entries(messageEntries)
            .build();

        sqsClient.sendMessageBatch(sendMessageBatchRequest);

        // Read a message from a queue

        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
            .waitTimeSeconds(10)
            .maxNumberOfMessages(10)
            .build();

        List<Message> sqsMessages = sqsClient.receiveMessage(receiveMessageRequest)
            .messages();

        sqsMessages.get(0)
            .attributes();
        sqsMessages.get(0)
            .body();

        // Delete a message from a queue
        DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
            .queueUrl(fifoQueueUrl)
            .receiptHandle(sqsMessages.get(0)
                .receiptHandle())
            .build();

        sqsClient.deleteMessage(deleteMessageRequest);

        // Monitoring
        GetQueueAttributesRequest getQueueAttributesRequestForMonitoring = GetQueueAttributesRequest.builder()
            .queueUrl(standardQueueUrl)
            .build();

        GetQueueAttributesResponse attributesResponse = sqsClient.getQueueAttributes(getQueueAttributesRequestForMonitoring);
        System.out.println(String.format("The number of messages on the queue: %s", attributesResponse.attributes()
            .get("ApproximateNumberOfMessages")));
        System.out.println(String.format("The number of messages in flight: %s", attributesResponse.attributes()
            .get("ApproximateNumberOfMessagesNotVisible")));

    }

}

package com.baeldung.sqs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.GetQueueAttributesRequest;
import com.amazonaws.services.sqs.model.GetQueueAttributesResult;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SetQueueAttributesRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.AmazonSQS;

public class SQSApplication {

    private static final AWSCredentials credentials;

    static {
        // put your accesskey and secretkey here
        credentials = new BasicAWSCredentials(
            "<AWS accesskey>", 
            "<AWS secretkey>"
        );
    }

    public static void main(String[] args) {

        // Set up the client
        AmazonSQS sqs = AmazonSQSClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.US_EAST_1)
            .build();

        // Create a standard queue

        CreateQueueRequest createStandardQueueRequest = new CreateQueueRequest("baeldung-queue");
        String standardQueueUrl = sqs.createQueue(createStandardQueueRequest)
            .getQueueUrl();

        System.out.println(standardQueueUrl);

        // Create a fifo queue

        Map<String, String> queueAttributes = new HashMap<String, String>();
        queueAttributes.put("FifoQueue", "true");
        queueAttributes.put("ContentBasedDeduplication", "true");

        CreateQueueRequest createFifoQueueRequest = new CreateQueueRequest("baeldung-queue.fifo").withAttributes(queueAttributes);
        String fifoQueueUrl = sqs.createQueue(createFifoQueueRequest)
            .getQueueUrl();

        System.out.println(fifoQueueUrl);

        // Set up a dead letter queue

        String deadLetterQueueUrl = sqs.createQueue("baeldung-dead-letter-queue")
            .getQueueUrl();

        GetQueueAttributesResult deadLetterQueueAttributes = sqs.getQueueAttributes(new GetQueueAttributesRequest(deadLetterQueueUrl).withAttributeNames("QueueArn"));

        String deadLetterQueueARN = deadLetterQueueAttributes.getAttributes()
            .get("QueueArn");

        SetQueueAttributesRequest queueAttributesRequest = new SetQueueAttributesRequest().withQueueUrl(standardQueueUrl)
            .addAttributesEntry("RedrivePolicy", "{\"maxReceiveCount\":\"2\", " + "\"deadLetterTargetArn\":\"" + deadLetterQueueARN + "\"}");

        sqs.setQueueAttributes(queueAttributesRequest);

        // Send a message to a standard queue

        Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();

        messageAttributes.put("AttributeOne", new MessageAttributeValue().withStringValue("This is an attribute")
            .withDataType("String"));

        SendMessageRequest sendMessageStandardQueue = new SendMessageRequest().withQueueUrl(standardQueueUrl)
            .withMessageBody("A simple message.")
            .withDelaySeconds(30) // Message will arrive in the queue after 30 seconds. We can use this only in standard queues
            .withMessageAttributes(messageAttributes);

        sqs.sendMessage(sendMessageStandardQueue);

        // Send a message to a fifo queue

        SendMessageRequest sendMessageFifoQueue = new SendMessageRequest().withQueueUrl(fifoQueueUrl)
            .withMessageBody("FIFO Queue")
            .withMessageGroupId("baeldung-group-1")
            .withMessageAttributes(messageAttributes);

        sqs.sendMessage(sendMessageFifoQueue);

        // Send multiple messages

        List<SendMessageBatchRequestEntry> messageEntries = new ArrayList<>();
        messageEntries.add(new SendMessageBatchRequestEntry().withId("id-1")
            .withMessageBody("batch-1")
            .withMessageGroupId("baeldung-group-1"));
        messageEntries.add(new SendMessageBatchRequestEntry().withId("id-2")
            .withMessageBody("batch-2")
            .withMessageGroupId("baeldung-group-1"));

        SendMessageBatchRequest sendMessageBatchRequest = new SendMessageBatchRequest(fifoQueueUrl, messageEntries);
        sqs.sendMessageBatch(sendMessageBatchRequest);

        // Read a message from a queue

        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(fifoQueueUrl).withWaitTimeSeconds(10) // Long polling;
            .withMaxNumberOfMessages(1); // Max is 10

        List<Message> sqsMessages = sqs.receiveMessage(receiveMessageRequest)
            .getMessages();

        sqsMessages.get(0)
            .getAttributes();
        sqsMessages.get(0)
            .getBody();

        // Delete a message from a queue

        sqs.deleteMessage(new DeleteMessageRequest().withQueueUrl(fifoQueueUrl)
            .withReceiptHandle(sqsMessages.get(0)
                .getReceiptHandle()));

        // Monitoring
        GetQueueAttributesRequest getQueueAttributesRequest = new GetQueueAttributesRequest(standardQueueUrl).withAttributeNames("All");
        GetQueueAttributesResult getQueueAttributesResult = sqs.getQueueAttributes(getQueueAttributesRequest);
        System.out.println(String.format("The number of messages on the queue: %s", getQueueAttributesResult.getAttributes()
            .get("ApproximateNumberOfMessages")));
        System.out.println(String.format("The number of messages in flight: %s", getQueueAttributesResult.getAttributes()
            .get("ApproximateNumberOfMessagesNotVisible")));

    }

}

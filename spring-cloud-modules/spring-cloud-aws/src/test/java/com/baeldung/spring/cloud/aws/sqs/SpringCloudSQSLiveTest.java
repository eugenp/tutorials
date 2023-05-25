package com.baeldung.spring.cloud.aws.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.PurgeQueueRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.baeldung.spring.cloud.aws.SpringCloudAwsTestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 
 * To run this Live Test, we need to have an AWS account and have API keys generated for programmatic access.
 * 
 * Check the README file in this module for more information.
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
public class SpringCloudSQSLiveTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringCloudSQSLiveTest.class);

    @Autowired
    @Lazy
    private SpringCloudSQS springCloudSQS;

    private static String receiveQueueName;
    private static String receiveQueueUrl;

    private static String sendQueueName;
    private static String sendQueueURl;

    @BeforeClass
    public static void setupAwsResources() {

        sendQueueName = UUID.randomUUID().toString();
        receiveQueueName = SpringCloudSQS.QUEUE_NAME;

        AmazonSQS amazonSQS = SpringCloudAwsTestUtil.amazonSQS();

        CreateQueueResult receiveQueue = amazonSQS.createQueue(receiveQueueName);
        receiveQueueUrl = receiveQueue.getQueueUrl();

        CreateQueueResult sendQueue = amazonSQS.createQueue(sendQueueName);
        sendQueueURl = sendQueue.getQueueUrl();
    }

    @Test
    public void whenMessageSentAndVerified_thenSuccess() throws InterruptedException {

        String message = "Hello World";
        springCloudSQS.send(sendQueueName, message);

        AmazonSQS amazonSQS = SpringCloudAwsTestUtil.amazonSQS();

        ReceiveMessageRequest request = new ReceiveMessageRequest(sendQueueURl);
        request.setMaxNumberOfMessages(1);

        ReceiveMessageResult result = null;
        do {
            result = amazonSQS.receiveMessage(request);
            if (result.getMessages().size() == 0) {
                logger.info("Message not received at first time, waiting for 1 second");
            }
        } while (result.getMessages().size() == 0);
        assertThat(result.getMessages().get(0).getBody()).isEqualTo(message);
        
        // Delete message so that it doen't interfere with other test
        amazonSQS.deleteMessage(sendQueueURl, result.getMessages().get(0).getReceiptHandle());
        
    }

    @Test
    public void whenConvertedMessageSentAndVerified_thenSuccess() throws InterruptedException, IOException {
        
        Greeting message = new Greeting("Hello", "World");
        springCloudSQS.send(sendQueueName, message);

        AmazonSQS amazonSQS = SpringCloudAwsTestUtil.amazonSQS();

        ReceiveMessageRequest request = new ReceiveMessageRequest(sendQueueURl);
        request.setMaxNumberOfMessages(1);

        ReceiveMessageResult result = null;
        do {
            result = amazonSQS.receiveMessage(request);
            if (result.getMessages().size() == 0) {
                logger.info("Message not received at first time, waiting for 1 second");
            }
        } while (result.getMessages().size() == 0);
        assertThat(new ObjectMapper().readValue(result.getMessages().get(0).getBody(), Greeting.class)).isEqualTo(message);
        
        // Delete message so that it doen't interfere with other test
        amazonSQS.deleteMessage(sendQueueURl, result.getMessages().get(0).getReceiptHandle());
    }
    
    @Test
    public void givenMessageSent_whenMessageReceived_thenSuccess() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        springCloudSQS.setCountDownLatch(countDownLatch);

        AmazonSQS amazonSQS = SpringCloudAwsTestUtil.amazonSQS();
        for (int i = 0; i < 5; i++) {
            amazonSQS.sendMessage(receiveQueueUrl, "Hello World " + i);
            logger.info("Sent message {}, waiting for 1 second", i + 1);
            Thread.sleep(1000L);
        }
        countDownLatch.await();
    }

    @AfterClass
    public static void cleanupAwsResources() {
        AmazonSQS amazonSQS = SpringCloudAwsTestUtil.amazonSQS();
        PurgeQueueRequest receiveQueuePurge = new PurgeQueueRequest(receiveQueueUrl);
        amazonSQS.purgeQueue(receiveQueuePurge);
        amazonSQS.deleteQueue(receiveQueueUrl);

        PurgeQueueRequest sendQueuePurge = new PurgeQueueRequest(sendQueueURl);
        amazonSQS.purgeQueue(sendQueuePurge);
        amazonSQS.deleteQueue(sendQueueURl);
    }
}

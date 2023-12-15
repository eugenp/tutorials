/**
 * These test cases have dependency with docker because they pull the docker images from docker hub
 * and run the container. So, please make sure to install docker before running the tests.
 * For the image details please look into the docker-compose files under resources/connectiondetails/docker
 **/
package com.baeldung.connectiondetails;

import com.baeldung.connectiondetails.configuration.RabbitMQConnectionDetailsConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConnectionDetailsApplication.class)
@Import(RabbitMQConnectionDetailsConfiguration.class)
@TestPropertySource(locations = {"classpath:connectiondetails/application-rabbitmq.properties"})
@ActiveProfiles("rabbitmq")
public class RabbitmqConnectionDetailsLiveTest {

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqConnectionDetailsLiveTest.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CachingConnectionFactory connectionFactory;

    private final static String queueName = "Test_Queue";

    RabbitAdmin rabbitAdmin;

    @Before
    public void setup() {
        logger.info("create new queue");
        rabbitAdmin = new RabbitAdmin(connectionFactory);
        logger.info("creating queue: " + rabbitAdmin.declareQueue(new Queue(queueName)));
        connectionFactory.destroy();
    }

    @After
    public void cleanup() {
        logger.info("delete queue");
        this.rabbitAdmin.deleteQueue(queueName, false, true);
    }
    @Test
    public void givenSecretVault_whenPublishMessageToRabbitmq_thenSuccess() {
        logger.info("sending message to queue " + queueName);
        final String MSG = "this is a test message";
        this.rabbitTemplate.convertAndSend(queueName, MSG);
        assertEquals(MSG, this.rabbitTemplate.receiveAndConvert(queueName));
    }
}

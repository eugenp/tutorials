package com.baeldung.springamqp.exponentialbackoff;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This live test requires:
 * 
 * - A running RabbitMQ instance on localhost (e.g. docker run -p 5672:5672 -p 15672:15672 --name rabbit rabbitmq:3-management)
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RabbitConfiguration.class })
public class ExponentialBackoffLiveTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObservableRejectAndDontRequeueRecoverer observableRecoverer;

    @Autowired
    private RetryQueuesInterceptor retryQueues;

    @Test
    public void whenSendToBlockingQueue_thenAllMessagesProcessed() throws Exception {
        int nb = 2;

        CountDownLatch latch = new CountDownLatch(nb);
        observableRecoverer.setObserver(() -> latch.countDown());

        for (int i = 1; i <= nb; i++) {
            rabbitTemplate.convertAndSend("blocking-queue", "blocking message " + i);
        }

        latch.await();
    }

    @Test
    public void whenSendToNonBlockingQueue_thenAllMessageProcessed() throws Exception {
        int nb = 2;

        CountDownLatch latch = new CountDownLatch(nb);
        retryQueues.setObserver(() -> latch.countDown());

        for (int i = 1; i <= nb; i++) {
            rabbitTemplate.convertAndSend("non-blocking-queue", "non-blocking message " + i);
        }

        latch.await();
    }
}

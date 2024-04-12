package com.baeldung.springamqp.exponentialbackoff;

import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

import com.rabbitmq.client.Channel;

@EnableRabbit
@Configuration
public class RabbitConfiguration {

    private static Logger logger = LoggerFactory.getLogger(RabbitConfiguration.class);

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue blockingQueue() {
        return QueueBuilder.nonDurable("blocking-queue")
            .build();
    }

    @Bean
    public Queue nonBlockingQueue() {
        return QueueBuilder.nonDurable("non-blocking-queue")
            .build();
    }

    @Bean
    public Queue retryWaitEndedQueue() {
        return QueueBuilder.nonDurable("retry-wait-ended-queue")
            .build();
    }

    @Bean
    public Queue retryQueue1() {
        return QueueBuilder.nonDurable("retry-queue-1")
            .deadLetterExchange("")
            .deadLetterRoutingKey("retry-wait-ended-queue")
            .build();
    }

    @Bean
    public Queue retryQueue2() {
        return QueueBuilder.nonDurable("retry-queue-2")
            .deadLetterExchange("")
            .deadLetterRoutingKey("retry-wait-ended-queue")
            .build();
    }

    @Bean
    public Queue retryQueue3() {
        return QueueBuilder.nonDurable("retry-queue-3")
            .deadLetterExchange("")
            .deadLetterRoutingKey("retry-wait-ended-queue")
            .build();
    }

    @Bean
    public RetryQueues retryQueues() {
        return new RetryQueues(1000, 3.0, 10000, retryQueue1(), retryQueue2(), retryQueue3());
    }

    @Bean
    public ObservableRejectAndDontRequeueRecoverer observableRecoverer() {
        return new ObservableRejectAndDontRequeueRecoverer();
    }

    @Bean
    public RetryOperationsInterceptor retryInterceptor() {
        return RetryInterceptorBuilder.stateless()
            .backOffOptions(1000, 3.0, 10000)
            .maxAttempts(5)
            .recoverer(observableRecoverer())
            .build();
    }

    @Bean
    public RetryQueuesInterceptor retryQueuesInterceptor(RabbitTemplate rabbitTemplate, RetryQueues retryQueues) {
        return new RetryQueuesInterceptor(rabbitTemplate, retryQueues);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory defaultContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);

        return factory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory retryContainerFactory(ConnectionFactory connectionFactory, RetryOperationsInterceptor retryInterceptor) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);

        Advice[] adviceChain = { retryInterceptor };
        factory.setAdviceChain(adviceChain);

        return factory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory retryQueuesContainerFactory(ConnectionFactory connectionFactory, RetryQueuesInterceptor retryInterceptor) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);

        Advice[] adviceChain = { retryInterceptor };
        factory.setAdviceChain(adviceChain);

        return factory;
    }

    @RabbitListener(queues = "blocking-queue", containerFactory = "retryContainerFactory")
    public void consumeBlocking(String payload) throws Exception {
        logger.info("Processing message from blocking-queue: {}", payload);

        throw new Exception("exception occured!");
    }

    @RabbitListener(queues = "non-blocking-queue", containerFactory = "retryQueuesContainerFactory", ackMode = "MANUAL")
    public void consumeNonBlocking(String payload) throws Exception {
        logger.info("Processing message from non-blocking-queue: {}", payload);

        throw new Exception("Error occured!");
    }

    @RabbitListener(queues = "retry-wait-ended-queue", containerFactory = "defaultContainerFactory")
    public void consumeRetryWaitEndedMessage(String payload, Message message, Channel channel) throws Exception {
        MessageProperties props = message.getMessageProperties();

        rabbitTemplate().convertAndSend(props.getHeader("x-original-exchange"), props.getHeader("x-original-routing-key"), message);
    }
}

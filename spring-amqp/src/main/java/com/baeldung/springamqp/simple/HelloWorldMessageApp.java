package com.baeldung.springamqp.simple;

import com.rabbitmq.client.Channel;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.backoff.FixedBackOffPolicy;

import java.util.stream.IntStream;

@SpringBootApplication
public class HelloWorldMessageApp {

    private static final boolean NON_DURABLE = false;
    private static final String MY_QUEUE_NAME = "myQueue";

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldMessageApp.class, args);
    }

    @Bean
    public ApplicationRunner runner(RabbitTemplate template, RabbitAdmin rabbitAdmin) {
        rabbitAdmin.purgeQueue(HelloWorldMessageApp.MY_QUEUE_NAME);
        return args -> {
            IntStream.range(1, 51).forEach(id -> template.convertAndSend("myQueue", "Hello, world@" + id));
        };
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    // number of message fetched at one time
    int prefetchCount = 25;
    // Number of messages in one transaction if transacted
    // if not transacted but AcknowledgeMode.AUTO no of message after which ack is sent back
    // by default the size in one so ack is sent for each message
    int batchSize = 1;

    // consumer
    int consumerCount = 1;
    // max concurrent consumer
    int maxConsumerCount = consumerCount;
    // total prefetchCount considering the concurrent consumers
    int totalPrefetchCount = consumerCount * prefetchCount;

    // kill -9
    // jps -v | grep HelloWorldMessageApp | cut -d " " -f1 | xargs kill -9
    // Process finished with exit code 137 (interrupted by signal 9: SIGKILL)

    @Bean
    public MethodInterceptor retryAdvice() {
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(1000);
        return RetryInterceptorBuilder.stateless()
                .backOffPolicy(backOffPolicy)
                .maxAttempts(300)
                .recoverer(new MessageRecoverer() {
                    @Override
                    public void recover(Message message, Throwable cause) {
                        System.out.println("last line of defense");
                        System.out.println("Write to file " + message + " throwing exception " + cause.getMessage());
                    }
                })
                .build();
    }

    ;

    @Bean
    @Primary
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setPrefetchCount(totalPrefetchCount);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setConcurrentConsumers(consumerCount);

        // this is really important when the acknowledgemode is Auto
        // but it's depricated in 2.2 amqp for batch size
        factory.setTxSize(batchSize);
        factory.setMaxConcurrentConsumers(maxConsumerCount);
        // may be we don't need the advice any more
        factory.setAdviceChain(retryAdvice());
        return factory;
    }


    @Bean
    public Queue myQueue() {
        Queue myQueue = new Queue(MY_QUEUE_NAME, NON_DURABLE);
        return myQueue;
    }

    @RabbitListener(
            queues = MY_QUEUE_NAME,
            containerFactory = "rabbitListenerContainerFactory")
    public void listen(String in, Channel channel) throws InterruptedException {
        System.out.println("Message read from myQueue : " + in);
        int msgNo = Integer.parseInt(in.split("@", 2)[1]);
        if (msgNo == 2) {
            throw new RuntimeException("Injected runtime exception " + msgNo);
        }
        if (msgNo == totalPrefetchCount - 1) {
            System.out.println("Prefetch " + totalPrefetchCount + ", batch=" + batchSize + " all messages would be put back ");
            //   System.exit(2);
        }
        System.out.println("Message processing done :" + in);
    }

}

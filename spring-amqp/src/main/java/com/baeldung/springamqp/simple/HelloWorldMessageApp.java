package com.baeldung.springamqp.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQImpl;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.util.stream.IntStream;

@SpringBootApplication
public class HelloWorldMessageApp {

    private static final boolean NON_DURABLE = false;
    private static final String MY_QUEUE_NAME = "myQueue";


    public static void main(String[] args) {
        SpringApplication.run(HelloWorldMessageApp.class, args);
    }

    @Bean
    public ApplicationRunner runner(RabbitTemplate template) {
        return args -> {
            IntStream.range(1, 51).forEach(id -> template.convertAndSend("myQueue", "Hello, world@" + id));
        };
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
    int batchSize=25;

    @Bean
    @Primary
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setPrefetchCount(prefetchCount);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setConcurrentConsumers(1);
        // this is really important when the acknowledgemode is Auto
        // but it's depricated in 2.2 amqp for batch size
        factory.setTxSize(batchSize);
        //factory.setMaxConcurrentConsumers(10);
        return factory;
    }


    @Bean
    public Queue myQueue() {
        Queue myQueue = new Queue(MY_QUEUE_NAME, NON_DURABLE);
        return myQueue;
    }

    @RabbitListener(queues = MY_QUEUE_NAME,containerFactory = "rabbitListenerContainerFactory")
    public void listen(String in, Channel channel) throws InterruptedException, IOException {
        System.out.println("Message read from myQueue : " + in);
        Thread.sleep(10);
        System.out.println("Message processing done :" + in);
        int msgNo = Integer.parseInt(in.split("@", 2)[1]);
        if (msgNo ==batchSize-1) {
            System.out.println("Prefetch " + prefetchCount + ", batch="+batchSize+" all messages would be put back ");
            //  Thread.sleep(1000000);
            System.exit(2);
        }
    }

}
